package com.cmcc.application.convertor;

import com.cmcc.domain.model.Meeting;
import com.cmcc.domain.model.PeriodMeeting;
import com.cmcc.representation.co.PeriodMeetingCreateCO;
import com.cmcc.representation.dto.MeetingDto;
import com.cmcc.representation.dto.PeriodDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author baiyanmin
 * @description 实体转换
 * @date 2020-09-18
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface PeriodMeetingConverMapper {
    PeriodMeetingConverMapper MAPPER = Mappers.getMapper(PeriodMeetingConverMapper.class);

    PeriodMeeting toModel(PeriodMeetingCreateCO periodMeetingCreateCO);

    List<PeriodDto> toPeriodList(List<PeriodMeeting> periodList);

}
