package com.cmcc.application.convertor;

import com.cmcc.domain.model.Attendee;
import com.cmcc.representation.co.JoinUserCO;
import com.cmcc.representation.dto.JoinUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author baiyanmin
 * @description 实体转换
 * @date 2020-09-22
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface AttendeeConvertorMapper {
    AttendeeConvertorMapper MAPPER = Mappers.getMapper(AttendeeConvertorMapper.class);

    Attendee toDto(JoinUserCO joinUserCO);

    List<Attendee> toAttendeeList(List<JoinUserCO> list);

    List<JoinUserDto> toJoinUserList(List<Attendee> list);
}
