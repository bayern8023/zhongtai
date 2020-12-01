package com.cmcc.representation.co;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class MeetingProlongCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 会议meetingid
     */
    @JsonProperty("meeting_id")
    private long meetingId;

    /*
     * 会议延迟时长
     */
    @JsonProperty("prolong_time_min")
    private long prolongTimeMin;

}
