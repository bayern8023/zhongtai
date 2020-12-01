package com.cmcc.application.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.application.convertor.AttendeeConvertorMapper;
import com.cmcc.application.convertor.MeetingConvertorMapper;
import com.cmcc.application.convertor.PeriodMeetingConverMapper;
import com.cmcc.application.enums.MeetingStatusEnum;
import com.cmcc.application.enums.MeetingTypeEnum;
import com.cmcc.application.utils.Result;
import com.cmcc.common.utils.DateUtil;
import com.cmcc.common.utils.UuidUtil;
import com.cmcc.domain.model.Attendee;
import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.repository.AttendeeRepo;
import com.cmcc.domain.repository.MeetingRepo;
import com.cmcc.infrastructure.client.yunshixun.MeetingApiServer;
import com.cmcc.infrastructure.utils.RedisUtil;
import com.cmcc.representation.co.JoinUserCO;
import com.cmcc.representation.co.MeetingEditCO;
import com.cmcc.representation.co.MeetingListQueryCO;
import com.cmcc.representation.co.MeetingProlongCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议服务
 * @author baiyanmin
 * @since 2020-09-12
 */
@Service(value="meetingService")
@Transactional(rollbackFor = Exception.class)
public class MeetingService {

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
     * @description 创建会议
     * @return
     */
    public Result createMeeting(String token,MeetingEditCO meetingEditCO) throws Exception {
        //请求云视讯创建会议
        JSONObject result = meetingApiServer.createMeeting(token,MeetingConvertorMapper.MAPPER.toEditDto(meetingEditCO));
        int resultCode = Integer.parseInt(result.get("code").toString());

        //保存周期会议信息
        if(resultCode == 200){
            Meeting meeting = new Meeting();
            meeting.setUuid(UuidUtil.get32UUID());
            meeting.setPmUuid("");
            meeting.setTemplateUuid(meetingEditCO.getTemplateUuid());
            meeting.setCreateTime(DateUtil.getTime());
            meeting.setMeetingDate(meetingEditCO.getMeetingTime());
            meeting.setMeetingTime(meetingEditCO.getMeetingTime());
            meeting.setMeetingTheme(meetingEditCO.getMeetingTheme());
            meeting.setMeetingIfmute(meetingEditCO.getMeetingIfmute());
            meeting.setMeetingLength(meetingEditCO.getMeetingLength());
            meeting.setMeetingType(meetingEditCO.getMeetingType());
            meeting.setMeetingAttendee(meetingEditCO.getMeetingAttendee());
            meeting.setMeetingId(Long.parseLong(result.get("data").toString()));
            meeting.setMeetingStatus("");
            meetingRepo.saveMeeting(meeting);

            List<JoinUserCO> joinUserCOList = meetingEditCO.getMeetingAttendee();
            if(joinUserCOList!=null && joinUserCOList.size()>0){
                //保存周期参会人
                JoinUserCO joinUserCO = new JoinUserCO();
                joinUserCO.setMobile(String.valueOf(redisUtil.get(token+"-mobile")));
                joinUserCOList.add(joinUserCO);
                attendeeRepo.addBatchAttendees(joinUserCOList,meeting.getUuid(),"MEETING");
            }
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 修改会议
     * @return
     */
    public Result updateMeeting(String token,MeetingEditCO meetingEditCO) throws Exception {
        //请求云视讯更新会议
        JSONObject result = meetingApiServer.updateMeeting(token,MeetingConvertorMapper.MAPPER.toEditDto(meetingEditCO));
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            //修改会议数据
            meetingRepo.updateMeeting(MeetingConvertorMapper.MAPPER.toEditDto(meetingEditCO));
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 编辑会议
     * @return
     */
    public Result editMeeting(String token,MeetingEditCO meetingEditCO) throws Exception {
        Meeting meeting = MeetingConvertorMapper.MAPPER.toEditDto(meetingEditCO);
        //请求云视讯更新会议
        JSONObject result = meetingApiServer.updateMeeting(token,meeting);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            //修改会议数据
            meetingRepo.updateMeeting(MeetingConvertorMapper.MAPPER.toEditDto(meetingEditCO));
            //修改会议参会人数据
            attendeeRepo.addBatchAttendees(meetingEditCO.getMeetingAttendee(),meetingEditCO.getUuid(),"MEETING");
        }else {
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 取消会议
     * @return
     */
    public Result cancelMeeting(String token,Long MeetingId) throws Exception {
        //请求云视讯取消会议
        JSONObject result = meetingApiServer.cencalMeeting(token,MeetingId);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            //根据会议meetingid删除会议
            meetingRepo.deleteMeetingForMeetingId(MeetingId);
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 删除会议
     * @return
     */
    public Result deleteMeetings(String token,String ids) throws Exception {
        //请求云视讯删除会议
        JSONObject result = meetingApiServer.deleteMeetings(token,ids);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            //根据会议meetingid删除会议
            meetingRepo.deleteMeetingForMeetingId(Long.parseLong(ids));
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 查询会议列表
     * @return
     */
    public Result getMeetingList(String token,MeetingListQueryCO meetingListQueryCO) throws Exception {
        //请求云视讯获取会议列表
        JSONObject result = meetingApiServer.getMeetingList(token,meetingListQueryCO);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 全场静音
     * @return
     */
    public Result setBoardroomSilenceState(String token,MeetingEditCO meetingEditCO) throws Exception {
        //请求云视讯会议全场静音
        JSONObject result = meetingApiServer.setBoardroomSilenceState(token,meetingEditCO);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 结束会议
     * @return
     */
    public Result endMeeting(String token,Long MeetingId) throws Exception {
        //请求云视讯结束会议
        JSONObject result = meetingApiServer.endMeeting(token,MeetingId);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 会议延迟
     * @return
     */
    public Result prolongMeeting(String token, MeetingProlongCO meetingProlongCO) throws Exception {
        //请求云视讯会议延迟
        JSONObject result = meetingApiServer.prolongMeeting(token,meetingProlongCO);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("data"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    /**
     * @author baiyanmin
     * @description 会议详情
     * @return
     */
    public Result getMeetingInfo(String token,Long meetingId) throws Exception {
        //请求云视讯会议详情
        JSONObject result = meetingApiServer.getMeetingInfo(token,meetingId);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            //根据meetingid获取会议数据
            Meeting meeting = meetingRepo.getMeetingForMeetingId(meetingId);
            JSONObject data = JSON.parseObject(result.get("data").toString());
            data.put("template_uuid",meeting.getTemplateUuid());
            return Result.ok().setData(data);
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }
}
