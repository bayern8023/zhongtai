/* Copyright©2020-2020 China Mobile Communications Group Co.,Ltd. All rights reserved. */
package com.cmcc.application.service;

import com.cmcc.application.convertor.AttendeeConvertorMapper;
import com.cmcc.application.convertor.TemplateConvertorMapper;
import com.cmcc.application.utils.PageData;
import com.cmcc.application.utils.Result;
import com.cmcc.common.utils.Tools;
import com.cmcc.common.utils.UuidUtil;
import com.cmcc.domain.event.TemplateEvent;
import com.cmcc.domain.model.Attendee;
import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.Template;
import com.cmcc.infrastructure.database.mapper.AttendeeMapper;
import com.cmcc.infrastructure.database.mapper.MeetingMapper;
import com.cmcc.infrastructure.database.mapper.PeriodMeetingMapper;
import com.cmcc.infrastructure.database.mapper.TemplateMapper;
import com.cmcc.infrastructure.utils.RedisUtil;
import com.cmcc.representation.co.AttendeeCO;
import com.cmcc.representation.co.SaveTemplateCO;
import com.cmcc.representation.co.TemplateCO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 会议服务
 * @author baiyanmin
 * @since 2020-09-12
 */
@Service(value="TemplateService")
@Transactional(rollbackFor = Exception.class)
public class TemplateService implements TemplateEvent<TemplateCO>{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TemplateMapper templateMapper;
    @Autowired
    private AttendeeMapper attendeeMapper;
    @Autowired
    private MeetingMapper meetingMapper;
    @Autowired
    private PeriodMeetingMapper periodMeetingMapper;
    @Autowired
    private RedisUtil redisUtil;


    public Result createTemplate(String token, SaveTemplateCO saveTemplateCO) {
        TemplateCO templateCO = TemplateConvertorMapper.MAPPER.toTemplateCO(saveTemplateCO);
        return saveTemplate(token, templateCO);
    }
    /**
     * 保存模板
     *
     * @param templateCO
     * @param token
     * @return
     */
    @Override
    public Result saveTemplate(String token, TemplateCO templateCO) {
        Result result = new Result();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }

        if (Tools.isEmpty(templateCO.getTemplateName())) {
            return result.setCode(10400).setMsg("无效请求").setDescription("参数错误，模板名称为空");
        }

        Template template = TemplateConvertorMapper.MAPPER.toTemplate(templateCO);
        template.setCreatePeople(userId);
        template.setMeetingHost(userId);
        String uuid = UuidUtil.get32UUID();
        template.setUuid(uuid);

        template.setCreateTime(getChinaTime());
        template.setUpdateTime(getChinaTime());
        String name = templateCO.getTemplateName();
        if (getWordCount(name) > 40) {
            return result.setCode(10400).setMsg("无效请求").setDescription("模板名称多于40字符");
        }

        try {
            if (!onlyTemplateName(userId, name)) {
                return result.setCode(100010).setMsg("模板名称重复");
            }

            templateMapper.saveTemplate(template);
            List<AttendeeCO> attendeeCOList = templateCO.getAttendeeCOList();

            if (attendeeCOList != null && attendeeCOList.size() > 0){
                List<Attendee> attendees = COToAttendee(attendeeCOList, uuid);
                attendeeMapper.addAttendees(attendees);
            }
        } catch (Exception e) {
            logger.error("保存模板失败" + e.getMessage());
            return result.setCode(100020).setMsg("数据库错误").setDescription("保存模板失败");
        }

        return result.setCode(200).setMsg("保存成功");
    }

    /**
     * 更新模板
     * @param token
     * @param templateCO
     * @return
     */
    @Override
    public Result updateTemplate(String token, TemplateCO templateCO) {
        Result result = new Result();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }

        String templateName = templateCO.getTemplateName();
        if (Tools.isEmpty(templateName)) {
            return result.setCode(10400).setMsg("无效请求").setDescription("模板名称为空");
        }

        Template template = TemplateConvertorMapper.MAPPER.toTemplate(templateCO);
        template.setCreatePeople(userId);

        template.setUpdateTime(getChinaTime());

        String uuid = template.getUuid();
        try {
            if (!onlyTemplateName(userId, templateName) && !templateMapper.tpNameUuid(userId, templateName).contains(template.getUuid())) {
                return result.setCode(100010).setMsg("模板名称重复");
            }
            templateMapper.updateTemplate(template);
            attendeeMapper.deleteAttendeesByUuid(uuid);
            if (templateCO.getAttendeeCOList() != null && templateCO.getAttendeeCOList().size() > 0) {
                attendeeMapper.addAttendees(COToAttendee(templateCO.getAttendeeCOList(), uuid));
            }

        } catch (Exception e) {
            logger.error("编辑失败" + e.getMessage());
            return result.setCode(100020).setMsg("数据库错误").setDescription("编辑失败");
        }

        return result.setCode(200).setMsg("修改成功");
    }

    /**
     * 删除模板
     * @param token
     * @param uuid
     * @return
     * @throws Exception
     */

    @Override
    public Result deleteTemplate(String token, String uuid) throws Exception {
        Result result = new Result();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }

        templateMapper.deleteTemplate(userId, uuid, getChinaTime());

        return result.setCode(200).setMsg("删除成功");

    }

    /**
     * 根据uuid查询模板信息
     * @param token
     * @param uuid
     * @return
     */
    @Override
    public Result getTemplateByUuid(String token, String uuid) {
        Result<TemplateCO> result = new Result<>();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }

        if (uuid == null) {
            return result.setCode(10400).setMsg("无效请求").setDescription("uuid为空");
        }
        Template template;
        TemplateCO templateCO;
        try {
        template = templateMapper.getTemplateByUuid(uuid);
        if (template == null) return result.setCode(100050).setMsg("未查询到数据");
        templateCO = TemplateConvertorMapper.MAPPER.toTemplateCO(template);
        List<Attendee> attendeeList = attendeeMapper.getAttendees(uuid);
        List<AttendeeCO> attendeeCOList = new ArrayList<>();
        if (attendeeList.size() > 0) {
            for (Attendee attendee:attendeeList) {
                AttendeeCO attendeeCO =  AttendeeConvertorMapper.MAPPER.toAttendeeCO(attendee);
                attendeeCOList.add(attendeeCO);
            }
        }
        templateCO.setAttendeeCOList(attendeeCOList);

        } catch (Exception e) {
            return result.setCode(100020).setMsg("数据库错误").setDescription("获取模板详情失败");
        }

        return result.setData(templateCO);
    }

    /**
     * 根据用户分页查询
     * @param token
     * @param page
     * @param size
     * @return
     * @throws Exception
     */
    @Override
    public Result getTemplateByOwner(String token, int page, int size) throws Exception {
        Result<PageData> result = new Result<>();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }

        List<TemplateCO> data = new ArrayList<>();
        List<Template> templates = null;
        int offset = 0;
        if (page > 0) {
            offset = (page - 1) * size;
        }
        int count = templateMapper.templateCount(userId);
        if (count < offset) {
            return result.setCode(10400).setMsg("无效请求").setDescription("分页参数不合法");
        }

        templates = templateMapper.getTemplateByCreatPeople(userId, offset, size);


        for (Template template:templates) {
            TemplateCO templateCO = TemplateConvertorMapper.MAPPER.toTemplateCO(template);
            List<Attendee> attendees;
            try {
                attendees = attendeeMapper.getAttendees(templateCO.getUuid());
            } catch (Exception e) {
                logger.error("获取全部模板详情失败" + e.getMessage());
                return result.setCode(100020).setMsg("数据库错误").setDescription("获取全部模板详情失败");
            }
            List<AttendeeCO> attendeeCOList = attendeeToCO(attendees);
            templateCO.setAttendeeCOList(attendeeCOList);
            data.add(templateCO);
        }

        PageData<TemplateCO> pageData = new PageData<>();
        pageData.setCount(count)
                .setData(data);
        return result.setData(pageData);
    }


    public Result toTemplate(String token, HashMap<String, String> requestMap, String type) throws Exception {
        Result result = new Result();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }
        String newUuid = UuidUtil.get32UUID();

        if (!requestMap.containsKey("id") || Tools.isEmpty(requestMap.get("id")) ||
        !requestMap.containsKey("name") || Tools.isEmpty(requestMap.get("name"))
        ) {
            return new Result().setCode(10400).setMsg("无效请求").setDescription("参数不正确");
        }
        String id = requestMap.get("id");
        String name = requestMap.get("name");
        if (getWordCount(name) > 40) {
            return result.setCode(10400).setMsg("无效请求").setDescription("模板名称多于40字符");
        }

        if (!onlyTemplateName(userId, name)) {
            return result.setCode(100010).setMsg("模板名称重复");
        }
        switch (type){
            case "MEETING":return meetingToTemplate(userId, id, name, newUuid);
            case "PERIODMEETING":return periodMeetingToTemplate(userId, id, name, newUuid);
        }
        return result.setCode(100030).setMsg("无效类型").setDescription("未查到此类型");
    }

    /**
     * 检测模板名称是否有重复
     * @param token
     * @param
     * @param map
     * @return
     */
    public Result ifOnlyName(String token, HashMap<String, String> map) throws Exception {
        Result<Boolean> result = new Result<>();

        String userId = String.valueOf(redisUtil.get(token));
        if(Tools.isEmpty(userId)){
            return result.setCode(10403).setMsg("用户未登录！");
        }

        if (!map.containsKey("name") || Tools.isEmpty(map.get("name"))) {
            return result.setCode(10400).setMsg("无效请求").setDescription("参数错误");
        }

        boolean data;

        data = onlyTemplateName(userId, map.get("name"));

        return result.setData(data);

    }

    /**
     * 参会人实体列表转化为
     * @param attendees
     * @return
     */
    private List<AttendeeCO> attendeeToCO(List<Attendee> attendees) {
        int size = attendees.size();
        List<AttendeeCO> result = new ArrayList<>(size);
        if (size == 0) return result;

        for (Attendee attendee:attendees) {
            AttendeeCO attendeeCO = AttendeeConvertorMapper.MAPPER.toAttendeeCO(attendee);
            result.add(attendeeCO);
        }
        return result;
    }

    /**
     *
     * @param attendeeCOList
     * @param uuid
     * @return
     */
    private List<Attendee> COToAttendee(List<AttendeeCO> attendeeCOList, String uuid){
        List<Attendee> result = new ArrayList<>(attendeeCOList.size());

        for (AttendeeCO attendeeCO:attendeeCOList) {
            Attendee attendee = AttendeeConvertorMapper.MAPPER.toAttendee(attendeeCO);
            attendee.setTypeTemplate();
            attendee.setUuid(uuid);
            result.add(attendee);
        }
        return result;
    }

    /**
     * 获取东八区的时间
     * @return
     */
    private String getChinaTime() {
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        return simpleDateFormat.format(new Date());
    }

    private boolean onlyTemplateName(String creatPeople, String name) throws Exception {
        return !templateMapper.ifOnlyName(creatPeople, name);
    }

    private int nameCount(String creatPeople, String name) throws Exception {
        return templateMapper.nameCount(creatPeople, name);
    }

    private Result meetingToTemplate(String creatPeople, String meetingId, String name, String newUuid) throws Exception {
        Meeting meeting;
        Result result = new Result();
        meeting = meetingMapper.getMeetingByMeetingId(meetingId);

        List<Attendee> attendees;

        Template template = new Template();
        boolean ifMute = true;
        if ("fale".equals(meeting.getMeetingIfmute())) {
            ifMute = false;
        }
        template.setUuid(newUuid)
                .setCreatePeople(creatPeople)
                .setCreateTime(getChinaTime())
                .setIfDelete(false)
                .setMeetingHost(creatPeople)
                .setTemplateName(name)
                .setUpdateTime(getChinaTime())
                .setMeetingIfmute(ifMute);

        try {
            templateMapper.saveTemplate(template);
            attendees = attendeeMapper.getAttendees(meeting.getUuid());
            if (attendees != null && attendees.size() > 0) {
                for (Attendee attendee:attendees) {
                    attendee.setUuid(newUuid);
                }
                attendeeMapper.addAttendees(attendees);
            }
        } catch (Exception e) {
            logger.error("数据库错误："+e.getMessage());
            return result.setCode(100002).setMsg("数据库错误").setDescription("保存模板失败");
        }
        return result.setCode(200).setMsg("保存成模板成功");
    }

    private Result periodMeetingToTemplate(String creatPeople, String pmUuid, String name, String newUuid) throws Exception {
        Result result = new Result();

        List<PeriodMeeting> pmList = periodMeetingMapper.getPeriodMeetingByUuid(pmUuid);
        if (pmList.size() == 0) {
            return result.setCode(100000000).setMsg("未查询到模板");
        }
        PeriodMeeting periodMeeting = pmList.get(0);
        List<Attendee> attendees;

        attendees = attendeeMapper.getAttendees(pmUuid);
        if (attendees != null && attendees.size() > 0) {
            for (Attendee attendee:attendees) {
                attendee.setUuid(newUuid);
            }
            attendeeMapper.addAttendees(attendees);
        }

        Template template = new Template();
        boolean ifMute = false;
        if ("true".equals(periodMeeting.getMeetingIfmute())) {
            ifMute = true;
        }
        template.setUuid(newUuid)
                .setCreatePeople(creatPeople)
                .setCreateTime(getChinaTime())
                .setIfDelete(false)
                .setMeetingHost(creatPeople)
                .setTemplateName(name)
                .setUpdateTime(getChinaTime())
                .setMeetingIfmute(ifMute);

        templateMapper.saveTemplate(template);

        return result.setCode(200).setMsg("保存成模板成功");
    }

    /**
     *获取包含中文字符串的字符长度
     * @param s
     * @return
     */
    private int getWordCount(String s)
    {
        int length = 0;
        for(int i = 0; i < s.length(); i++)
        {
            int ascii = Character.codePointAt(s, i);
            if(ascii >= 0 && ascii <=255)
                length++;
            else
                length += 2;

        }
        return length;

    }
}

