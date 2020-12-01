/* Copyright©2020-2020 China Mobile Communications Group Co.,Ltd. All rights reserved. */
package com.cmcc.infrastructure.database.mapper;

import com.cmcc.domain.model.Attendee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendeeMapper {

    void addAttendees(List<Attendee> list) throws Exception;

    List<Attendee> getAttendees(@Param("uuid") String uuid) throws Exception;

    void deleteAttendeesByUuid(@Param("uuid")String uuid) throws Exception;

}
