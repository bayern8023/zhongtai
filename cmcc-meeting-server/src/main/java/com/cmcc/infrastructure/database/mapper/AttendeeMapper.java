package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.Attendee;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AttendeeMapper {

    List<Attendee> getAttendeeForPeriod(String uuid);

    List<Attendee> getPeriodListForMobile(String mobile);

    void deleteAttendees(String uuid);

    void addBatchAttendees(List<Attendee> list);


}
