package com.cmcc.representation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 会议信息表
 * @author baiyanmin
 * @since 2020-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MeetingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 会议uuid
     */
    private String uuid;

    /*
     * 周期uuid
     */
    private String pmUuid;

    /*
     * 会议meetingid
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long meetingId;

    /*
     * 会议名
     */
    private String meetingTheme;

    /*
     * 会议时间
     */
    private String meetingTime;

    /*
     * 会议状态
     */
    private String meetingStatus;

    /*
     * 会议类型
     */
    private String meetingType;

    /*
     * 会议是否静音
     */
    private String meetingIfmute;

    /*
     * 操作人
     */
    private String operatorEcUid;

    /*
     * 会议时长
     */
    private int meetingLength;

    /*
     * 创建时间
     */
    private String createTime;

}
