/* Copyright©2020-2020 China Mobile Communications Group Co.,Ltd. All rights reserved. */
package com.cmcc.representation.facade;

import com.cmcc.application.service.TemplateService;
import com.cmcc.representation.co.SaveTemplateCO;
import com.cmcc.representation.co.TemplateCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/template")
public class TemplateController extends BaseController {
	
	@Autowired
	private TemplateService templateService;

	/**
	 * 创建模板
	 * @param token
	 * @param saveTemplateCO
	 * @return
	 */
	@PostMapping("/create_template")
	public Object createTemplate(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestBody SaveTemplateCO saveTemplateCO) throws Exception {

		return templateService.createTemplate(token, saveTemplateCO);

	}

	/**
	 * 根据模板uuid获取模板信息
	 * @param uuid
	 * @return
	 */
	@GetMapping("/get_template_uuid")
	public Object getTemplateByUuid(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestParam("uuid") String uuid) throws Exception {
		return templateService.getTemplateByUuid(token, uuid);
	}

	/**
	 * 根据模板创建人获取模板信息
	 * @return
	 */
	@GetMapping("/get_template_owner")
	public Object getTemplateByOwner(@RequestHeader("X-ACCESS-TOKEN") String token,  @RequestParam("page")int page,  @RequestParam("size")int size) throws Exception {

		return templateService.getTemplateByOwner(token, page, size) ;
	}

	/**
	 * 根据创建人和模板id删除模板
	 * @param uuid
	 * @return
	 */
	//todo 改成post
	@GetMapping("/delete_template")
	public Object deleteTemplate(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestParam("uuid") String uuid) throws Exception {

		return templateService.deleteTemplate(token, uuid);
	}

	/**
	 * 修改模板信息
	 * @param templateCO
	 * @return
	 */
	@PostMapping("/update_template")
	public Object updateTemplate(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestBody TemplateCO templateCO) throws Exception {

		return templateService.updateTemplate(token, templateCO);
	}

	/**
	 * 周期会议保存成模板
	 * @param token
	 * @param requestMap
	 * @return
	 */
	@PostMapping("/pmeeting_to_template")
	public Object periodMeetingToTemplate(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestBody HashMap<String, String> requestMap) throws Exception {

		return templateService.toTemplate(token, requestMap, "PERIODMEETING");
	}

	/**
	 * 会议保存成模板
	 * @param token
	 * @param requestMap
	 * @return
	 */
	@PostMapping("/meeting_to_template")
	public Object meetingToTemplate(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestBody HashMap<String, String> requestMap) throws Exception {

		return templateService.toTemplate(token, requestMap, "MEETING");
	}

	/**
	 * 模板名称唯一性判断
	 * @param token
	 * @return
	 */
	@PostMapping("/if_only_name")
	public Object ifOnlyName(@RequestHeader("X-ACCESS-TOKEN") String token, @RequestBody HashMap<String, String> requestMap) throws Exception {
		return templateService.ifOnlyName(token, requestMap);
	}

}
