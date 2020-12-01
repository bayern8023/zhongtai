package com.cmcc.representation.facade;

import com.cmcc.application.service.LoginService;
import com.cmcc.representation.co.LoginCO;
import com.cmcc.representation.co.MeetingEditCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class LoginController extends BaseController {
	
	@Autowired
	private LoginService loginService;


	/**
	 * 获取登录token
	 * @return
	 */
	@PostMapping("/get_token")
	public Object periodMeetingList(@RequestBody LoginCO loginCO)throws Exception{
		return loginService.getToken(loginCO);
	}

}
