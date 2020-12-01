package com.cmcc.infrastructure.repository.impl;

import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.domain.model.PeriodQuery;
import com.cmcc.domain.repository.MeetingRepo;
import com.cmcc.infrastructure.database.mapper.MeetingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "meetingRepo")
public class MeetingRepoImpl implements MeetingRepo {

	@Autowired
	private MeetingMapper meetingMapper;
	

	/**
	 * 保存会议
	 * @return
	 * @throws Exception
	 */
	@Override
	public void saveMeeting(Meeting meeting) throws Exception {
		meetingMapper.saveMeeting(meeting);
	}

	/**
	 * 更新会议id
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateMeetingId(Meeting meeting) throws Exception {
		meetingMapper.updateMeetingId(meeting);
	}

	/**
	 * 查询会议信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public Meeting getMeetingInfo(Meeting meeting) throws Exception {
		return meetingMapper.getMeetingInfo(meeting);
	}

	/**
	 * 查询会议信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public Meeting getMeetingForMeetingId(Long meetingId) throws Exception {
		return meetingMapper.getMeetingForMeetingId(meetingId);
	}

	/**
	 * 获取周期会议列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Meeting> getPeriodMeetingList(PeriodQuery periodQuery) throws Exception {
		return meetingMapper.getPeriodMeetingList(periodQuery);
	}

	/**
	 * 获取周期会议列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Meeting> getPeriodMeetingList(String uuid) throws Exception {
		return meetingMapper.getPeriodMeetingLists(uuid);
	}

	/**
	 * 获取周期会议列表总数
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Meeting> getPeriodMeetingListCount(String uuid) throws Exception {
		return meetingMapper.getPeriodMeetingListCount(uuid);
	}

	/**
	 * 获取周期最近一条会议信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public Meeting getMeetingForPmuuid(String pmUuid) throws Exception {
		return meetingMapper.getMeetingForPmuuid(pmUuid);
	}

	/**
	 * 更新会议信息
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateMeeting(Meeting meeting) throws Exception {
		meetingMapper.updateMeeting(meeting);
	}

	/**
	 * 删除多个会议
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteMeeting(String pmUuid) throws Exception {
		meetingMapper.deleteMeeting(pmUuid);
	}

	/**
	 * 删除单个会议
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deleteMeetingForMeetingId(Long meetingId) throws Exception {
		meetingMapper.deleteMeetingForMeetingId(meetingId);
	}
}



