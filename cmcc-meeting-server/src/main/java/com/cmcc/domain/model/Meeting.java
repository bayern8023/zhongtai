package com.cmcc.domain.model;

import com.cmcc.representation.co.JoinUserCO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 会议信息表
 * @author baiyanmin
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 会议uuid
     */
    private String uuid;

    /*
     * 周期uud
     */
    private String pmUuid;

    /*
     * 模版uuid
     */
    private String templateUuid;

    /*
     * 会议meetingid
     */
    private long meetingId;

    /*
     * 会议标题
     */
    private String meetingTheme;

    /*
     * 会议时间
     */
    private String meetingTime;

    /*
     * 会议日期
     */
    private String meetingDate;

    /*
     * 会议状态
     */
    private String meetingStatus;

    /*
     * 是否静音
     */
    private String meetingIfmute;

    /*
     * 操作人
     */
    private String operatorEcUid;

    /*
     * 会议时长
     */
    private Double meetingLength;

    /*
     * 会议类型
     */
    private String meetingType;

    /*
     * 参会人列表
     */
    private List<JoinUserCO> meetingAttendee;

    /*
     * 创建时间
     */
    private String createTime;

}
