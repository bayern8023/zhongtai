package com.cmcc.infrastructure.client.tongxunlu;

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

@Service(value = "mailListApiServer")
public class MailListApiServer {

	@Autowired
	private RedisUtil redisUtil;

	private String mailListUrl = "https://contacts.125339.com.cn/access/rest/v200/";

	//获取登录通讯录token
	public String getMailToken(String token){
		return String.valueOf(redisUtil.get(token+"-mail"));
	}

	//通讯录获取token
	public JSONObject getTokenForMailList(String Sign,String identity,LoginCO loginCO) throws Exception {
		String address = mailListUrl+"token";
		Map<String, Object> params = new HashMap<>();
		params.put("mobile", loginCO.getMobile());
		params.put("password", loginCO.getPassword());
		params.put("identity", identity);
		params.put("sign", Sign);
		return HttpRequestUtil.httpGet(address,"",params);
	}

	//通讯录获取企业一级部门
	public JSONObject getDepartmentList(String token,String enterpriseId) throws Exception {
		String address = mailListUrl+enterpriseId+"/department/list";
		String tokens = getMailToken(token);
		return HttpRequestUtil.httpGet(address,tokens,null);
	}

	//通讯录获取企业一级部门下级部门
	public JSONObject getDepartmentListForParent(String token,String enterpriseId,String pdepartmentId) throws Exception {
		String address = mailListUrl+enterpriseId+"/department/list";
		Map<String, Object> params = new HashMap<>();
		params.put("pdepartmentId", pdepartmentId);
		return HttpRequestUtil.httpGet(address,getMailToken(token),params);
	}

	//通讯录获取企业下的员工
	public JSONObject getUserListForEnterpriseId(String token,String enterpriseId,String key,int start,int limit) throws Exception {
		String address = mailListUrl+enterpriseId+"/user/list";
		Map<String, Object> params = new HashMap<>();
		params.put("enterpriseId", enterpriseId);
		params.put("key",key);
		params.put("start", start);
		params.put("limit", limit);
		return HttpRequestUtil.httpGet(address,getMailToken(token),params);
	}

	//通讯录获取企业下部门的员工
	public JSONObject getUserListForDepartmentId(String token,String enterpriseId,String pdepartmentId,int start,int limit) throws Exception {
		String address = mailListUrl+enterpriseId+"/user/list/"+pdepartmentId;
		Map<String, Object> params = new HashMap<>();
		params.put("start", start);
		params.put("limit", limit);
		return HttpRequestUtil.httpGet(address,getMailToken(token),params);
	}
}

