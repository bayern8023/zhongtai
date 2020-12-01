package com.cmcc.application.convertor;

import com.cmcc.domain.model.Attendee;
import com.cmcc.representation.co.AttendeeCO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(unmappedSourcePolicy = IGNORE)
public interface AttendeeConvertorMapper {

    AttendeeConvertorMapper MAPPER = Mappers.getMapper(AttendeeConvertorMapper.class);

    /**
     * 转化成attendee实体
     * @param attendeeCO
     * @return
     */
    Attendee toAttendee(AttendeeCO attendeeCO);

    /**
     * 转化成AttendeeCO实体
     * @param attendee
     * @return
     */
    AttendeeCO toAttendeeCO(Attendee attendee);
}
