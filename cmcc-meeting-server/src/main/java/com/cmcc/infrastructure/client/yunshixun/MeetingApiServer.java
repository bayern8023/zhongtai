package com.cmcc.infrastructure.client.yunshixun;

import com.alibaba.fastjson.JSONObject;
import com.cmcc.application.utils.HttpRequestUtil;
import com.cmcc.domain.model.Meeting;
import com.cmcc.infrastructure.utils.RedisUtil;
import com.cmcc.representation.co.LoginCO;
import com.cmcc.representation.co.MeetingEditCO;
import com.cmcc.representation.co.MeetingListQueryCO;
import com.cmcc.representation.co.MeetingProlongCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "meetingApiServer")
public class MeetingApiServer {

	@Autowired
	private RedisUtil redisUtil;

	private String apiUrl = "https://meeting.125339.com.cn/mixapi/";

	//获取用户所属的终端类型
	public String getBussinessType(String token){
		return String.valueOf(redisUtil.get(token+"-businessType"));
	}

	//发起预约会议
	public JSONObject createMeeting(String token, Meeting meeting) throws Exception {
		String address = apiUrl+"createMeeting";
		Map<String, Object> bodyParameters = new HashMap<>();
		bodyParameters.put("meeting_operate_type", getBussinessType(token));
		bodyParameters.put("meeting_theme", meeting.getMeetingTheme());
		bodyParameters.put("meeting_ifmute", meeting.getMeetingIfmute().equals("true")?true:false);
		bodyParameters.put("meeting_length", meeting.getMeetingLength()*60*60);
		bodyParameters.put("meeting_mode", "HD");
		bodyParameters.put("meeting_starttime", meeting.getMeetingTime());
		bodyParameters.put("meeting_type", meeting.getMeetingType());
		bodyParameters.put("meeting_attendee", meeting.getMeetingAttendee());
		return HttpRequestUtil.httpRequest(address,"POST",bodyParameters,token);
	}

	//修改预约会议
	public JSONObject updateMeeting(String token, Meeting meeting) throws Exception {
		String address = apiUrl+"updateMeeting";
		Map<String, Object> bodyParameters = new HashMap<>();
		bodyParameters.put("meeting_operate_type", getBussinessType(token));
		bodyParameters.put("meeting_id", meeting.getMeetingId());
		bodyParameters.put("meeting_theme", meeting.getMeetingTheme());
		bodyParameters.put("meeting_ifmute", meeting.getMeetingIfmute().equals("true")?true:false);
		bodyParameters.put("meeting_length", meeting.getMeetingLength()*60*60);
		bodyParameters.put("meeting_mode", "HD");
		bodyParameters.put("meeting_starttime", meeting.getMeetingTime());
		bodyParameters.put("meeting_type", "0");
		bodyParameters.put("meeting_attendee", meeting.getMeetingAttendee());
		return HttpRequestUtil.httpRequest(address,"POST",bodyParameters,token);
	}

	//取消预约会议
	public JSONObject cencalMeeting(String token,Long meeting_id) throws Exception {
		String address = apiUrl+"cancelMeeting";
		Map<String, Object> bodyParameters = new HashMap<>();
		bodyParameters.put("meeting_operate_type", getBussinessType(token));
		bodyParameters.put("meeting_id", meeting_id);
		return HttpRequestUtil.httpRequest(address,"POST",bodyParameters,token);
	}

	//获取token
	public JSONObject getToken(LoginCO loginCO) throws Exception {
		String address = apiUrl+"token";
		Map<String, Object> params = new HashMap<>();
		params.put("identity", loginCO.getIdentity());
		params.put("key", loginCO.getKey());
		params.put("mobile", loginCO.getMobile());
		params.put("password", loginCO.getPassword());
		return HttpRequestUtil.httpGet(address,"",params);
	}

	//删除会议
	public JSONObject deleteMeetings(String token,String ids) throws Exception {
		String address = apiUrl+"deleteMeetings";
		Map<String, Object> params = new HashMap<>();
		params.put("meeting_operate_type", getBussinessType(token));
		params.put("ids", ids);
		return HttpRequestUtil.httpGet(address,token,params);
	}

	//查询会议列表
	public JSONObject getMeetingList(String token,MeetingListQueryCO meetingListQueryCO) throws Exception {
		String address = apiUrl+"getMeetingList";
		Map<String, Object> params = new HashMap<>();
		params.put("meeting_operate_type", getBussinessType(token));
		params.put("meeting_filter", meetingListQueryCO.getMeetingFilter());
		params.put("page", meetingListQueryCO.getPage());
		params.put("page_size", meetingListQueryCO.getPageSize());
		return HttpRequestUtil.httpGet(address,token,params);
	}

	//全场静音
	public JSONObject setBoardroomSilenceState(String token,MeetingEditCO meetingEditCO) throws Exception {
		String address = apiUrl+"setBoardroomSilenceState";
		Map<String, Object> target = new HashMap<>();
		target.put("meeting_id", Long.parseLong(meetingEditCO.getMeetingId()));
		target.put("meeting_ifmute", meetingEditCO.getMeetingIfmute().equals("true")?true:false);
		Map<String, Object> params = new HashMap<>();
		params.put("target",target);
		params.put("meeting_operate_type", getBussinessType(token));
		return HttpRequestUtil.httpRequest(address,"POST",params,token);
	}

	//结束会议
	public JSONObject endMeeting(String token,Long MeetingId) throws Exception {
		String address = apiUrl+"endMeeting";
		Map<String, Object> params = new HashMap<>();
		params.put("meeting_operate_type", getBussinessType(token));
		params.put("meeting_id", MeetingId);
		return HttpRequestUtil.httpRequest(address,"POST",params,token);
	}

	//会议延迟
	public JSONObject prolongMeeting(String token, MeetingProlongCO MeetingProlongCO) throws Exception {
		String address = apiUrl+"prolongMeeting";
		Map<String, Object> target = new HashMap<>();
		target.put("meeting_id", MeetingProlongCO.getMeetingId());
		target.put("prolong_time_min", MeetingProlongCO.getProlongTimeMin());
		Map<String, Object> params = new HashMap<>();
		params.put("target",target);
		params.put("meeting_operate_type", getBussinessType(token));
		return HttpRequestUtil.httpRequest(address,"POST",params,token);
	}

	//会议详情
	public JSONObject getMeetingInfo(String token,Long MeetingId) throws Exception {
		String address = apiUrl+"getMeetingInfo";
		Map<String, Object> params = new HashMap<>();
		params.put("meeting_operate_type", getBussinessType(token));
		params.put("meeting_id", MeetingId);
		return HttpRequestUtil.httpGet(address,token,params);
	}

}

