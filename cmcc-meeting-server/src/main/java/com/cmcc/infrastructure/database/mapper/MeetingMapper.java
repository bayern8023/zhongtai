package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.model.PeriodQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingMapper {


	public void saveMeeting(Meeting meeting) throws Exception;

	public void updateMeetingId(Meeting meeting) throws Exception;

	public Meeting getMeetingInfo(Meeting meeting) throws Exception;

	public Meeting getMeetingForMeetingId(Long meetingId) throws Exception;

	public List<Meeting> getPeriodMeetingList(PeriodQuery periodQuery) throws Exception;

	public List<Meeting> getPeriodMeetingLists(String uuid) throws Exception;

	public List<Meeting> getPeriodMeetingListCount(String uuid) throws Exception;

	public Meeting getMeetingForPmuuid(String pmUuid) throws Exception;

	public void updateMeeting(Meeting meeting) throws Exception;

	public void deleteMeeting(String pmUuid) throws Exception;

	public void deleteMeetingForMeetingId(Long meetingId) throws Exception;
}

