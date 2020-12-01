package com.cmcc.representation.facade;

import com.cmcc.application.service.MeetingService;
import com.cmcc.representation.co.MeetingEditCO;
import com.cmcc.representation.co.MeetingListQueryCO;
import com.cmcc.representation.co.MeetingProlongCO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/meeting")
public class MeetingController extends BaseController {
	
	@Autowired
	private MeetingService meetingService;

	/**
	 * 创建即时预约会议
	 * @return
	 */
	@PostMapping("/create_meeting")
	public Object createMeeting(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody MeetingEditCO meetingEditCO)throws Exception{
		return meetingService.createMeeting(token,meetingEditCO);
	}

	/**
	 * 修改预约会议
	 * @return
	 */
	@PostMapping("/update_meeting")
	public Object updateMeeting(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody MeetingEditCO meetingEditCO)throws Exception{
		return meetingService.updateMeeting(token,meetingEditCO);
	}


//	/**
//	 * 修改周期的会议
//	 * @return
//	 */
//	@PostMapping("/edit_meeting")
//	public Object editMeeting(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody MeetingEditCO meetingEditCO)throws Exception{
//		return meetingService.editMeeting(token,meetingEditCO);
//	}

	/**
	 * 取消会议
	 * @return
	 */
	@PostMapping("/cancel_meeting")
	public Object cancelMeeting(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "meeting_id") Long meeting_id)throws Exception{
		return meetingService.cancelMeeting(token,meeting_id);
	}

	/**
	 * 删除会议
	 * @return
	 */
	@PostMapping("/delete_meeting")
	public Object deleteMeetings(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "ids") String ids)throws Exception{
		return meetingService.deleteMeetings(token,ids);
	}

	/**
	 * 会议列表
	 * @return
	 */
	@GetMapping("/meeting_list")
	public Object getMeetingList(@RequestHeader("X-ACCESS-TOKEN") String token,
								 @RequestParam(value = "meeting_filter") String meetingFilter,
								 @RequestParam(value = "page") int page,
								 @RequestParam(value = "page_size") int pageSize)throws Exception{
		MeetingListQueryCO meetingListQueryCO = new MeetingListQueryCO();
		meetingListQueryCO.setMeetingFilter(meetingFilter);
		meetingListQueryCO.setPage(page);
		meetingListQueryCO.setPageSize(pageSize);
		return meetingService.getMeetingList(token,meetingListQueryCO);
	}

	/**
	 * 全场静音
	 * @return
	 */
	@PostMapping("/set_boardroom_silence")
	public Object setBoardroomSilenceState(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody MeetingEditCO meetingEditCO)throws Exception{
		return meetingService.setBoardroomSilenceState(token,meetingEditCO);
	}

	/**
	 * 结束会议
	 * @return
	 */
	@PostMapping("/end_meeting")
	public Object endMeeting(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "meeting_id") Long meeting_id)throws Exception{
		return meetingService.endMeeting(token,meeting_id);
	}

	/**
	 * 会议延迟
	 * @return
	 */
	@PostMapping("/prolong_meeting")
	public Object prolongMeeting(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestBody MeetingProlongCO meetingProlongCO)throws Exception{
		return meetingService.prolongMeeting(token,meetingProlongCO);
	}

	/**
	 * 会议详情
	 * @return
	 */
	@GetMapping("/meeting_info")
	public Object getMeetingInfo(@RequestHeader("X-ACCESS-TOKEN") String token,@RequestParam(value = "meeting_id") Long meeting_id)throws Exception{
		return meetingService.getMeetingInfo(token,meeting_id);
	}
}
