package com.cmcc.representation.co;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.assertj.core.condition.Join;

import java.io.Serializable;
import java.util.List;

/**
 * 周期会议信息表
 * @author baiyanmin
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MeetingEditCO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String uuid;

    @JsonProperty("meeting_id")
    private String meetingId;

    @JsonProperty("template_uuid")
    private String templateUuid;

    @JsonProperty("meeting_theme")
    private String meetingTheme;

    @JsonProperty("meeting_time")
    private String meetingTime;

    private String hostEcUid;

    private String operatorEcUid;

    @JsonProperty("meeting_ifmute")
    private String meetingIfmute;

    @JsonProperty("meeting_length")
    private Double meetingLength;

    @JsonProperty("meeting_type")
    private String meetingType;

    @JsonProperty("meeting_attendee")
    private List<JoinUserCO> meetingAttendee;
}
