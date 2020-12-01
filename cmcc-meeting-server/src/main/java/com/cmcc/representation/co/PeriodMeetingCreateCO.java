package com.cmcc.representation.co;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 周期会议信息表
 * @author baiyanmin
 * @since 2020-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PeriodMeetingCreateCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    @NotEmpty(message = "会议主题不能为空")
    private String meetingTheme;

    private String templateUuid;

    @NotEmpty(message = "周期会议开始时间不能为空")
    private String periodStarttime;

    @NotEmpty(message = "周期会议结束时间不能为空")
    private String periodEndtime;

    @NotEmpty(message = "周期会议日期不能为空")
    private String meetingDate;

    @NotEmpty(message = "周期会议时间不能为空")
    private String meetingTime;

    @NotEmpty(message = "会议类型不能为空")
    private String periodType;

    @NotEmpty(message = "主持人不能为空")
    private String hostEcUid;

    @NotEmpty(message = "创建人不能为空")
    private String operatorEcUid;

    @NotEmpty(message = "静音状态不能为空")
    private String meetingIfmute;

    @NotEmpty(message = "会议时常不能为空")
    private Double meetingLength;

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
