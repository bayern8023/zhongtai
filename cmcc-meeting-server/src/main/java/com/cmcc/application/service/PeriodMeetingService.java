package com.cmcc.application.service;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.application.convertor.AttendeeConvertorMapper;
import com.cmcc.application.convertor.MeetingConvertorMapper;
import com.cmcc.application.convertor.PeriodMeetingConverMapper;
import com.cmcc.application.enums.MeetingStatusEnum;
import com.cmcc.application.enums.MeetingTypeEnum;
import com.cmcc.application.enums.PeriodTypeEnum;
import com.cmcc.application.utils.Result;
import com.cmcc.common.utils.DateUtil;
import com.cmcc.common.utils.Tools;
import com.cmcc.common.utils.UuidUtil;
import com.cmcc.domain.model.Attendee;
import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.PeriodQuery;
import com.cmcc.domain.repository.AttendeeRepo;
import com.cmcc.domain.repository.MeetingRepo;
import com.cmcc.domain.repository.PeriodMeetingRepo;
import com.cmcc.infrastructure.client.yunshixun.MeetingApiServer;
import com.cmcc.infrastructure.utils.RedisUtil;
import com.cmcc.representation.co.JoinUserCO;
import com.cmcc.representation.co.PeriodMeetingCreateCO;
import com.cmcc.representation.co.PeriodQueryCO;
import com.cmcc.representation.dto.MeetingDto;
import com.cmcc.representation.dto.PeriodDto;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author baiyanmin
 * @description 周期会议核心业务
 * @date 2020-09-12
 */
@Service(value="periodMeetingService")
@Transactional(rollbackFor = Exception.class)
public class PeriodMeetingService {

    @Resource(name = "periodMeetingRepo")
    private PeriodMeetingRepo periodMeetingRepo;

    @Resource(name = "meetingRepo")
    private MeetingRepo meetingRepo;

    @Resource(name = "meetingApiServer")
    private MeetingApiServer meetingApiServer;

    @Resource(name = "attendeeRepo")
    private AttendeeRepo attendeeRepo;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * @author baiyanmin
     * @description 创建周期会议
     * @return
     */
    public synchronized Result savePeriodMeeting(String token,PeriodMeetingCreateCO periodMeetingCreateCO) throws Exception {

        //获取登录用户id
        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return Result.ok().setCode(10403).setMsg("登录状态失效，请重新登录！");
        }

        try {

            //查询创建人与周期名称是否重复
            PeriodQuery periodQuery = new PeriodQuery();
            periodQuery.setOperatorEcUid(userId);
            periodQuery.setMeetingTheme(periodMeetingCreateCO.getMeetingTheme());
            PeriodMeeting periodMeeting = periodMeetingRepo.getPeriodInfo(periodQuery);
            if(periodMeeting!=null){
                return Result.ok().setCode(1001).setMsg("不能重复创建周期");
            }

            periodMeetingCreateCO.setUuid(UuidUtil.get32UUID());
            periodMeetingCreateCO.setCreateTime(DateUtil.getTime());
            periodMeetingCreateCO.setUpdateTime(periodMeetingCreateCO.getCreateTime());
            periodMeetingCreateCO.setHostEcUid(userId);
            periodMeetingCreateCO.setOperatorEcUid(userId);
            //如果是按天，计算每天时间
            if(periodMeetingCreateCO.getPeriodType().equals(PeriodTypeEnum.MEETING_TYPE_0.getV())){
                String everyDay = periodMeetingCreateCO.getPeriodStarttime();
                long daySub = DateUtil.getDaySub(periodMeetingCreateCO.getPeriodStarttime(),periodMeetingCreateCO.getPeriodEndtime());
                for (int i=1;i<=daySub;i++){
                    everyDay += ","+DateUtil.getAfterDayDate(periodMeetingCreateCO.getPeriodStarttime(),i+"");
                }
                periodMeetingCreateCO.setMeetingDate(everyDay);
            }

            //根据周期会议日期生成具体的会议
            String meetingDates =  periodMeetingCreateCO.getMeetingDate();
            String[] meetingDate = null;
            if(meetingDates.contains(",")){
                meetingDate = meetingDates.split(",");
            }else{
                meetingDate = new String[1];
                meetingDate[0] = meetingDates;
            }
            for (int i=0;i<meetingDate.length;i++){

                Meeting meeting = new Meeting();
                meeting.setUuid(UuidUtil.get32UUID());
                meeting.setTemplateUuid(periodMeetingCreateCO.getTemplateUuid());
                meeting.setPmUuid(periodMeetingCreateCO.getUuid());
                meeting.setCreateTime(periodMeetingCreateCO.getCreateTime());
                meeting.setMeetingDate(meetingDate[i]);
                meeting.setMeetingTime(meetingDate[i]+" "+periodMeetingCreateCO.getMeetingTime());
                meeting.setMeetingTheme(periodMeetingCreateCO.getMeetingTheme());
                meeting.setMeetingIfmute(periodMeetingCreateCO.getMeetingIfmute());
                meeting.setMeetingLength(periodMeetingCreateCO.getMeetingLength());
                meeting.setMeetingType(MeetingTypeEnum.MEETING_TYPE_0.getV());
                meeting.setMeetingAttendee(periodMeetingCreateCO.getMeetingAttendee());

                //请求云视讯创建会议
                JSONObject result = meetingApiServer.createMeeting(token,meeting);
                int resultCode = Integer.parseInt(result.get("code").toString());

                //保存周期会议信息
                if(resultCode == 200){
                    meeting.setMeetingId(Long.parseLong(result.get("data").toString()));
                    meeting.setMeetingStatus(MeetingStatusEnum.W.getK());
                    meetingRepo.saveMeeting(meeting);
                }

                if(resultCode != 200){
                    return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
                }
            }

            //保存周期会议
            periodMeetingRepo.savePeriodMeeting(PeriodMeetingConverMapper.MAPPER.toModel(periodMeetingCreateCO));
            List<JoinUserCO> joinUserCOList = periodMeetingCreateCO.getMeetingAttendee();
            if(joinUserCOList==null){
                joinUserCOList = new ArrayList<>();
            }
            //保存周期参会人
            JoinUserCO joinUserCO = new JoinUserCO();
            joinUserCO.setMobile(String.valueOf(redisUtil.get(token+"-mobile")));
            joinUserCOList.add(joinUserCO);
            attendeeRepo.addBatchAttendees(joinUserCOList,periodMeetingCreateCO.getUuid(),"PERIOD");
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 周期列表
     * @return
     */
    public Result getPeriodList(String token,PeriodQuery periodQuery) throws Exception {
        //获取登录用户id
        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return Result.ok().setCode(10403).setMsg("登录状态失效，请重新登录！");
        }

        Map<String, Object> retResult = new HashMap<>();

        //查询状态下所有周期列表数据
        List<PeriodDto> periodList= new ArrayList<PeriodDto>();
        List<Attendee> periodUuidList = attendeeRepo.getPeriodListForMobile(String.valueOf(redisUtil.get(token+"-mobile")));
        if(periodUuidList!=null&&periodUuidList.size()>0){
            periodQuery.setPeriodList(periodUuidList);
            List<PeriodMeeting> list = periodMeetingRepo.getPeriodList(periodQuery);
            for(PeriodMeeting periodMeeting:list){
                //查询本周期最近会议的数据
                Meeting meeting = meetingRepo.getMeetingForPmuuid(periodMeeting.getUuid());
                if (meeting==null){
                    continue;
                }
                PeriodDto periodDto = new PeriodDto();
                periodDto.setUuid(periodMeeting.getUuid());
                periodDto.setMeetingTheme(periodMeeting.getMeetingTheme());
                periodDto.setPeriodTime(periodMeeting.getPeriodStarttime()+"-"+periodMeeting.getPeriodEndtime());
                periodDto.setPeriodType(periodMeeting.getPeriodType());
                periodDto.setOperatorEcUid(periodMeeting.getOperatorEcUid());
                //查询会议最新状态
                JSONObject result = meetingApiServer.getMeetingInfo(token,meeting.getMeetingId());
                int resultCode = Integer.parseInt(result.get("code").toString());
                if(resultCode == 10404){
                    continue;
                }
                if(resultCode != 200){
                    return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
                }

                JSONObject data = JSONObject.parseObject(result.get("data").toString());
                periodDto.setMeetingStatus(data.getString("meeting_status"));
                periodDto.setMeetingNextTime(data.getString("meeting_starttime"));
                periodList.add(periodDto);
            }
            retResult.put("pageSize", periodMeetingRepo.getPeriodListCount(periodQuery));
        }else{
            retResult.put("pageSize", 0);
        }
        retResult.put("periodList", periodList);


        return Result.ok().setData(retResult);
    }

    /**
     * @author baiyanmin
     * @description 周期会议列表
     * @return
     */
    public Result getPeriodMeetingList(String token,PeriodQuery periodQuery) throws Exception {
        Map<String, Object> retResult = new HashMap<>();

        List<Meeting> periodMeetingList = meetingRepo.getPeriodMeetingList(periodQuery);
        List<MeetingDto> meetingList= new ArrayList<MeetingDto>();
        for(Meeting meetings:periodMeetingList){
            MeetingDto meetingDto = new MeetingDto();
            //查询会议最新状态
            JSONObject result = meetingApiServer.getMeetingInfo(token,meetings.getMeetingId());
            int resultCode = Integer.parseInt(result.get("code").toString());
            if(resultCode != 200){
                return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
            }

            JSONObject data = JSONObject.parseObject(result.get("data").toString());

            meetingDto.setUuid(meetings.getUuid());
            meetingDto.setPmUuid(meetings.getPmUuid());
            meetingDto.setMeetingId(meetings.getMeetingId());
            meetingDto.setMeetingStatus(data.getString("meeting_status"));
            meetingDto.setMeetingTime(data.getString("meeting_starttime"));
            meetingDto.setMeetingTheme(data.getString("meeting_theme"));
            meetingDto.setOperatorEcUid(data.getString("operator_id"));
            meetingList.add(meetingDto);
        }

        retResult.put("meetingList", meetingList);
        retResult.put("pageSize", meetingRepo.getPeriodMeetingListCount(periodQuery.getUuid()));

        return Result.ok().setData(retResult);
    }

    /**
     * @author baiyanmin
     * @description 周期详情
     * @return
     */
    public Result getPeriodInfo(String token,String uuid) throws Exception {
        //获取登录用户id
        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return Result.ok().setCode(10403).setMsg("登录状态失效，请重新登录！");
        }

        //查询周期详情
        PeriodQuery periodQuery = new PeriodQuery();
        periodQuery.setOperatorEcUid(userId);
        periodQuery.setUuid(uuid);
        PeriodMeeting periodMeeting = periodMeetingRepo.checkPeriodInfo(periodQuery);
        if(null == periodMeeting){
            return Result.ok().setCode(1000).setMsg("周期不存在");
        }

        //查询周期下参会人
        List<Attendee> attendeeList = attendeeRepo.getAttendeeForPeriod(uuid);
        periodMeeting.setMeetingAttendee(AttendeeConvertorMapper.MAPPER.toJoinUserList(attendeeList));

        Map<String, Object> retResult = new HashMap<>();
        retResult.put("periodMeeting", periodMeeting);

        return Result.ok().setData(retResult);
    }

    /**
     * @author baiyanmin
     * @description 编辑周期会议
     * @return
     */
    public synchronized Result editPeriodMeeting(String token,PeriodMeetingCreateCO periodMeetingCreateCO) throws Exception {
        //获取登录用户id
        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return Result.ok().setCode(10403).setMsg("登录状态失效，请重新登录！");
        }
        String uuid = periodMeetingCreateCO.getUuid();
        if(Tools.isEmpty(uuid)){
            return Result.ok().setCode(1000).setMsg("周期不存在");
        }

        try {
            //查询周期详情
            PeriodQuery periodQuery = new PeriodQuery();
            periodQuery.setOperatorEcUid(userId);
            periodQuery.setUuid(uuid);
            PeriodMeeting periodMeeting = periodMeetingRepo.checkPeriodInfo(periodQuery);
            if(null == periodMeeting){
                return Result.ok().setCode(1000).setMsg("周期不存在");
            }

            periodMeetingCreateCO.setUpdateTime(DateUtil.getTime());
            periodMeetingCreateCO.setHostEcUid(userId);
            periodMeetingCreateCO.setOperatorEcUid(userId);

            //删除现有周期的会议
            List<Meeting> meetingList = meetingRepo.getPeriodMeetingList(periodMeetingCreateCO.getUuid());
            for (Meeting meeting:meetingList) {
                JSONObject result = meetingApiServer.cencalMeeting(token,meeting.getMeetingId());
                int resultCode = Integer.parseInt(result.get("code").toString());

                //修改周期的会议信息
                if(resultCode == 200){
                    meetingRepo.deleteMeeting(periodMeetingCreateCO.getUuid());
                }

                if(resultCode != 200 && resultCode != 10130){
                    return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
                }
            }

            //如果是按天，计算每天时间
            if(periodMeetingCreateCO.getPeriodType().equals(PeriodTypeEnum.MEETING_TYPE_0.getV())){
                String everyDay = periodMeetingCreateCO.getPeriodStarttime();
                long daySub = DateUtil.getDaySub(periodMeetingCreateCO.getPeriodStarttime(),periodMeetingCreateCO.getPeriodEndtime());
                for (int i=1;i<=daySub;i++){
                    everyDay += ","+DateUtil.getAfterDayDate(periodMeetingCreateCO.getPeriodStarttime(),i+"");
                }
                periodMeetingCreateCO.setMeetingDate(everyDay);
            }

            //根据周期会议日期生成具体的会议
            String meetingDates =  periodMeetingCreateCO.getMeetingDate();
            String[] meetingDate = null;
            if(meetingDates.contains(",")){
                meetingDate = meetingDates.split(",");
            }else{
                meetingDate = new String[1];
                meetingDate[0] = meetingDates;
            }
            for (int i=0;i<meetingDate.length;i++){

                Meeting meeting = new Meeting();
                meeting.setUuid(UuidUtil.get32UUID());
                meeting.setTemplateUuid(periodMeetingCreateCO.getTemplateUuid());
                meeting.setPmUuid(periodMeetingCreateCO.getUuid());
                meeting.setCreateTime(periodMeetingCreateCO.getUpdateTime());
                meeting.setMeetingDate(meetingDate[i]);
                meeting.setMeetingTime(meetingDate[i]+" "+periodMeetingCreateCO.getMeetingTime());
                meeting.setMeetingTheme(periodMeetingCreateCO.getMeetingTheme());
                meeting.setMeetingIfmute(periodMeetingCreateCO.getMeetingIfmute());
                meeting.setMeetingLength(periodMeetingCreateCO.getMeetingLength());
                meeting.setMeetingType(MeetingTypeEnum.MEETING_TYPE_0.getV());
                meeting.setMeetingAttendee(periodMeetingCreateCO.getMeetingAttendee());

                JSONObject result = meetingApiServer.createMeeting(token,meeting);
                int resultCode = Integer.parseInt(result.get("code").toString());

                //修改周期的会议信息
                if(resultCode == 200){
                    meeting.setMeetingId(Long.parseLong(result.get("data").toString()));
                    meeting.setMeetingStatus(MeetingStatusEnum.W.getK());
                    meetingRepo.saveMeeting(meeting);
                }

                if(resultCode != 200){
                    return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
                }
            }

            //保存周期会议
            periodMeetingRepo.editPeriodMeeting(PeriodMeetingConverMapper.MAPPER.toModel(periodMeetingCreateCO));
            List<JoinUserCO> joinUserCOList = periodMeetingCreateCO.getMeetingAttendee();
            if(joinUserCOList==null){
                joinUserCOList = new ArrayList<>();
            }
            //保存周期参会人
            JoinUserCO joinUserCO = new JoinUserCO();
            joinUserCO.setMobile(String.valueOf(redisUtil.get(token+"-mobile")));
            joinUserCOList.add(joinUserCO);
            attendeeRepo.addBatchAttendees(joinUserCOList,periodMeetingCreateCO.getUuid(),"PERIOD");
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 删除周期
     * @return
     */
    public synchronized Result deletePeriodMeeting(String token,String uuid) throws Exception {
        //获取登录用户id
        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return Result.ok().setCode(10403).setMsg("登录状态失效，请重新登录！");
        }
        if(Tools.isEmpty(uuid)){
            return Result.ok().setCode(1000).setMsg("周期不存在");
        }

        try {
            //查询周期详情
            PeriodQuery periodQuery = new PeriodQuery();
            periodQuery.setOperatorEcUid(userId);
            periodQuery.setUuid(uuid);
            PeriodMeeting periodMeeting = periodMeetingRepo.checkPeriodInfo(periodQuery);
            if(null == periodMeeting){
                return Result.ok().setCode(1000).setMsg("周期不存在");
            }

            //删除现有周期的会议
            List<Meeting> meetingList = meetingRepo.getPeriodMeetingList(uuid);
            for (Meeting meeting:meetingList) {
                JSONObject result = meetingApiServer.cencalMeeting(token,meeting.getMeetingId());
                int resultCode = Integer.parseInt(result.get("code").toString());

                //修改周期的会议信息
                if(resultCode == 200){
                    meetingRepo.deleteMeeting(uuid);
                }

                if(resultCode != 200 && resultCode != 10130){
                    return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
                }
            }

            //删除周期
            periodMeetingRepo.deletePeriod(uuid);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return Result.ok();
    }


}
