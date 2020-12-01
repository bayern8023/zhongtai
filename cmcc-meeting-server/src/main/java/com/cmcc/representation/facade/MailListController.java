package com.cmcc.representation.facade;

import com.cmcc.application.service.MailListService;
import com.cmcc.application.service.MeetingService;
import com.cmcc.representation.co.MeetingEditCO;
import com.cmcc.representation.co.MeetingListQueryCO;
import com.cmcc.representation.co.MeetingProlongCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maillist")
public class MailListController extends BaseController {
	
	@Autowired
	private MailListService mailListService;

	/**
	 * 获取企业一级部门
	 * @return
	 */
	@GetMapping("/department_list")
	public Object getDepartmentList(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "enterpriseId") String enterpriseId)throws Exception{
		return mailListService.getDepartmentList(token,enterpriseId);
	}

	/**
	 * 通讯录获取企业一级部门下级部门
	 * @return
	 */
	@GetMapping("/department_list_forparent")
	public Object getDepartmentListForParent(@RequestHeader("X-ACCESS-TOKEN") String token,
								 @RequestParam(value = "enterpriseId") String enterpriseId,
								 @RequestParam(value = "pdepartmentId") String pdepartmentId)throws Exception{
		return mailListService.getDepartmentListForParent(token,enterpriseId,pdepartmentId);
	}

	/**
	 * 通讯录获取企业下的员工
	 * @return
	 */
	@GetMapping("/userlist_for_enterpriseid")
	public Object getUserListForEnterpriseId(@RequestHeader("X-ACCESS-TOKEN") String token,
											 @RequestParam(value = "enterpriseId") String enterpriseId,
											 @RequestParam(value = "key") String key,
											 @RequestParam(value = "start") int start,
											 @RequestParam(value = "limit") int limit)throws Exception{
		return mailListService.getUserListForEnterpriseId(token,enterpriseId,key,start,limit);
	}

	/**
	 * 通讯录获取企业下部门的员工
	 * @return
	 */
	@GetMapping("/userlist_for_departmentid")
	public Object getUserListForDepartmentId(@RequestHeader("X-ACCESS-TOKEN") String token,
											 @RequestParam(value = "enterpriseId") String enterpriseId,
											 @RequestParam(value = "pdepartmentId") String pdepartmentId,
											 @RequestParam(value = "start") int start,
											 @RequestParam(value = "limit") int limit)throws Exception{
		return mailListService.getUserListForDepartmentId(token,enterpriseId,pdepartmentId,start,limit);
	}


}
