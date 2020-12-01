package com.cmcc.representation.dto;

import com.cmcc.representation.co.JoinUserCO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 周期会议信息表
 * @author baiyanmin
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PeriodMeetingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 周期uuid
     */
    private String uuid;

    /*
     * 周期名
     */
    private String meetingTheme;

    /*
     * 周期模版uuid
     */
    private String templateUuid;

    /*
     * 周期开始时间
     */
    private String periodStarttime;

    /*
     * 周期结束时间
     */
    private String periodEndtime;

    /*
     * 周期日期数组
     */
    private String[] meetingDate;

    /*
     * 周期名
     */
    private String meetingTime;

    /*
     * 周期类型
     */
    private String periodType;

    /*
     * 周期创建人
     */
    private String hostEcUid;

    /*
     * 周期操作人
     */
    private String operatorEcUid;

    /*
     * 会议是否静音
     */
    private String meetingIfmute;

    /*
     * 会议时长
     */
    private String meetingLength;

    /*
     * 创建时间
     */
    private String createTime;

    /*
     * 更新时间
     */
    private String updateTime;

    /*
     * 参会人列表
     */
    private List<JoinUserCO> meetingAttendee;
}
