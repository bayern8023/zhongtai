package com.cmcc.application.convertor;

import com.cmcc.domain.model.Meeting;
import com.cmcc.representation.co.MeetingEditCO;
import com.cmcc.representation.dto.MeetingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author baiyanmin
 * @description 实体转换
 * @date 2020-09-12
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface MeetingConvertorMapper {
    MeetingConvertorMapper MAPPER = Mappers.getMapper(MeetingConvertorMapper.class);

    @Mappings({
            @Mapping(source="meetingType", target="meetingType"),
            @Mapping(source="meetingIfmute", target="meetingIfmute")
    })
    Meeting toEditDto(MeetingEditCO meetingEditCO);

    List<MeetingDto> toMeetingList(List<Meeting> periodMeetingList);
}
