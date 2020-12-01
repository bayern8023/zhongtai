package com.cmcc.representation.facade;

import com.cmcc.application.service.PeriodMeetingService;
import com.cmcc.domain.model.PeriodQuery;
import com.cmcc.representation.co.PeriodMeetingCreateCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/period")
public class PeriodMeetingController extends BaseController {
	
	@Autowired
	private PeriodMeetingService periodMeetingService;

	/**
	 * 发起周期会议
	 * @return
	 */
	@PostMapping("/period_create")
	public Object periodCreate(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody PeriodMeetingCreateCO periodMeetingCreateCO)throws Exception{
		return periodMeetingService.savePeriodMeeting(token,periodMeetingCreateCO);
	}

	/**
	 * 周期列表
	 * @return
	 */
	@GetMapping("/period_list")
	public Object periodList(@RequestHeader("X-ACCESS-TOKEN") String token,
							 @RequestParam(value = "page") int page,
							 @RequestParam(value = "page_size") int pageSize)throws Exception{
		PeriodQuery periodQuery = new PeriodQuery();
		periodQuery.setPage((page-1)*pageSize);
		periodQuery.setPageSize(pageSize);
		return periodMeetingService.getPeriodList(token,periodQuery);
	}

	/**
	 * 周期的会议列表
	 * @return
	 */
	@GetMapping("/meeting_list")
	public Object periodMeetingList(@RequestHeader("X-ACCESS-TOKEN") String token,
									@RequestParam(value = "uuid") String uuid,
									@RequestParam(value = "page") int page,
									@RequestParam(value = "page_size") int pageSize)throws Exception{
		PeriodQuery periodQuery = new PeriodQuery();
		periodQuery.setPage((page-1)*pageSize);
		periodQuery.setPageSize(pageSize);
		periodQuery.setUuid(uuid);
		return periodMeetingService.getPeriodMeetingList(token,periodQuery);
	}

	/**
	 * 周期详情
	 * @return
	 */
	@GetMapping("/period_info")
	public Object periodInfo(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "uuid") String uuid)throws Exception{
		return periodMeetingService.getPeriodInfo(token,uuid);
	}

	/**
	 * 编辑周期
	 * @return
	 */
	@PostMapping("/period_edit")
	public Object periodEdit(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody PeriodMeetingCreateCO periodMeetingCreateCO)throws Exception{
		return periodMeetingService.editPeriodMeeting(token,periodMeetingCreateCO);
	}

	/**
	 * 删除周期
	 * @return
	 */
	@PostMapping("/period_delete")
	public Object periodDelete(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "uuid") String uuid)throws Exception{
		return periodMeetingService.deletePeriodMeeting(token,uuid);
	}

}
