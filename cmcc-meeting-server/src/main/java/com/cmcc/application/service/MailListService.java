package com.cmcc.application.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.application.utils.Result;
import com.cmcc.application.utils.SignUtil;
import com.cmcc.common.utils.Tools;
import com.cmcc.infrastructure.client.tongxunlu.MailListApiServer;
import com.cmcc.infrastructure.client.yunshixun.MeetingApiServer;
import com.cmcc.infrastructure.utils.RedisUtil;
import com.cmcc.representation.co.LoginCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 通讯录服务
 * @author baiyanmin
 * @since 2020-09-30
 */
@Service(value="mailListService")
@Transactional(rollbackFor = Exception.class)
public class MailListService {

    @Resource(name = "mailListApiServer")
    private MailListApiServer mailListApiServer;

    @Autowired
    private RedisUtil redisUtil;

    //获取企业一级
    public Result getDepartmentList(String token, String enterpriseId) throws Exception {
        JSONObject result = mailListApiServer.getDepartmentList(token,enterpriseId);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("dataList"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    //通讯录获取企业一级部门下级部门
    public Result getDepartmentListForParent(String token, String enterpriseId, String pdepartmentId) throws Exception {
        JSONObject result = mailListApiServer.getDepartmentListForParent(token,enterpriseId,pdepartmentId);
        int resultCode = Integer.parseInt(result.get("code").toString());
        int total = Integer.parseInt(result.get("total").toString());

        if(resultCode == 200 && total>0){
            return Result.ok().setData(result.get("dataList"));
        }
        if(resultCode == 200 && total==0){
            return Result.ok().setData(new ArrayList<>());
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    //通讯录获取企业下的员工
    public Result getUserListForEnterpriseId(String token, String enterpriseId,String key,int start,int limit) throws Exception {
        JSONObject result = mailListApiServer.getUserListForEnterpriseId(token,enterpriseId,key,start,limit);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("dataList"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }

    //通讯录获取企业下部门的员工
    public Result getUserListForDepartmentId(String token, String enterpriseId,String pdepartmentId,int start,int limit) throws Exception {
        JSONObject result = mailListApiServer.getUserListForDepartmentId(token,enterpriseId,pdepartmentId,start,limit);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            return Result.ok().setData(result.get("dataList"));
        }

        if(resultCode != 200){
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }

        return Result.ok();
    }
}
