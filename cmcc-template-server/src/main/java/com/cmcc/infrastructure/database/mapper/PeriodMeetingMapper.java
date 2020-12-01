package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.PeriodMeeting;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodMeetingMapper {
    List<PeriodMeeting> getPeriodMeetingByUuid(String uuid) throws Exception;
}
