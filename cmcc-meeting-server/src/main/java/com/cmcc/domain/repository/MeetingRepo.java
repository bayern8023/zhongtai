package com.cmcc.domain.repository;

import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.PeriodQuery;

import java.util.List;

public interface MeetingRepo {


	/**
	 * 保存会议
	 * @return
	 * @throws Exception
	 */
	public void saveMeeting(Meeting meeting) throws Exception;

	/**
	 * 更新会议id
	 * @return
	 * @throws Exception
	 */
	public void updateMeetingId(Meeting meeting) throws Exception;


	/**
	 * 查询会议信息
	 * @return
	 * @throws Exception
	 */
	public Meeting getMeetingInfo(Meeting meeting) throws Exception;

	/**
	 * 查询会议信息
	 * @return
	 * @throws Exception
	 */
	public Meeting getMeetingForMeetingId(Long meetingId) throws Exception;

	/**
	 * 查询周期会议列表
	 * @return
	 * @throws Exception
	 */
	public List<Meeting> getPeriodMeetingList(PeriodQuery periodQuery) throws Exception;

	/**
	 * 查询周期会议列表
	 * @return
	 * @throws Exception
	 */
	public List<Meeting> getPeriodMeetingList(String uuid) throws Exception;

	/**
	 * 查询周期会议列表
	 * @return
	 * @throws Exception
	 */
	public List<Meeting> getPeriodMeetingListCount(String uuid) throws Exception;


	/**
	 * 周期最近一条会议信息
	 * @return
	 * @throws Exception
	 */
	public Meeting getMeetingForPmuuid(String pmUuid) throws Exception;

	/**
	 * 更新会议信息
	 * @return
	 * @throws Exception
	 */
	public void updateMeeting(Meeting meeting) throws Exception;

	/**
	 * 删除周期下会议
	 * @return
	 * @throws Exception
	 */
	public void deleteMeeting(String pmUuid) throws Exception;

	/**
	 * 删除周期下会议
	 * @return
	 * @throws Exception
	 */
	public void deleteMeetingForMeetingId(Long meetingId) throws Exception;
}

