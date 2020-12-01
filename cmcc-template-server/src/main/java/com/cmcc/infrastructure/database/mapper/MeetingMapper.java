package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.Meeting;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingMapper {

	Meeting getMeetingByMeetingId(String meetingId) throws Exception;
}

