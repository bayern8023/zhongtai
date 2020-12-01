package com.cmcc.application.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmcc.application.utils.Result;
import com.cmcc.application.utils.SignUtil;
import com.cmcc.infrastructure.client.tongxunlu.MailListApiServer;
import com.cmcc.infrastructure.client.yunshixun.MeetingApiServer;
import com.cmcc.infrastructure.utils.RedisUtil;
import com.cmcc.representation.co.LoginCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取token服务
 * @author baiyanmin
 * @since 2020-09-23
 */
@Service(value="loginService")
@Transactional(rollbackFor = Exception.class)
public class LoginService {

    @Resource(name = "meetingApiServer")
    private MeetingApiServer meetingApiServer;

    @Resource(name = "mailListApiServer")
    private MailListApiServer mailListApiServer;

    @Autowired
    private RedisUtil redisUtil;

    // 秘钥
    private static final String KEY = "a859297ea56b53e84f2575be3efb0780";
    // 标识
    private static final String IDENTITY = "a86bc655ddfbda935dc3d16fef774997";

    /**
     * @author baiyanmin
     * @description 登录
     * @return
     */
    public Result getToken(LoginCO loginCO) throws Exception {
        JSONObject result = meetingApiServer.getToken(loginCO);
        int resultCode = Integer.parseInt(result.get("code").toString());

        if(resultCode == 200){
            //登录信息存到redis
            JSONObject data = JSON.parseObject(result.get("data").toString());
            String accessToken = data.getString("accessToken");
            Long expiresTime = Long.parseLong(data.getString("expiresTime"));
            //设置用户登录token
            redisUtil.set(accessToken,data.getString("userId"),expiresTime);
            //设置登录用户所属终端类型
            String businessType = String.valueOf(data.get("businessType"));
            String zdtype = "";
            if(businessType.indexOf("6")>-1){
                zdtype = "2";
            }
            if(businessType.indexOf("7")>-1){
                zdtype = "0";
            }
            //缓存用户账号所属终端类型
            redisUtil.set(accessToken+"-businessType",zdtype,expiresTime);
            //缓存用户手机号，用于查询该账号下参与的周期会议
            redisUtil.set(accessToken+"-mobile",loginCO.getMobile(),expiresTime);

            //通讯录接口签名
            String sign = getTxlSign(loginCO.getMobile(),loginCO.getPassword());
            JSONObject mailResult = mailListApiServer.getTokenForMailList(sign,IDENTITY,loginCO);
            String mailToken = mailResult.get("token").toString();
            //设置用户登录通讯录token
            redisUtil.set(accessToken+"-mail",mailToken,expiresTime);


            return Result.ok().setData(data);
        }else {
            return Result.failure().setCode(resultCode).setMsg(result.get("msg").toString());
        }
    }

    //获取通讯录sign
    private String getTxlSign(String mobile,String password){
        Map<String, String> parameters = new HashMap<>();
        // HTTP 参数列表
        parameters.put("mobile", mobile);
        parameters.put("password", password);
        parameters.put("identity", IDENTITY);
        return SignUtil.sign(KEY, parameters);
    }
}
