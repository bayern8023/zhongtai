package com.cmcc.domain.model;

import com.cmcc.representation.co.JoinUserCO;
import com.cmcc.representation.dto.JoinUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
public class PeriodMeeting implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 周期uuid
     */
    private String uuid;

    /*
     * 周期标题
     */
    private String meetingTheme;

    /*
     * 模版uuid
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
     * 会议日期
     */
    private String meetingDate;

    /*
     * 会议时间
     */
    private String meetingTime;

    /*
     * 周期类型
     */
    private String periodType;

    /*
     * 创建人
     */
    private String hostEcUid;

    /*
     * 操作人
     */
    private String operatorEcUid;

    /*
     * 会议是否静音
     */
    private String meetingIfmute;

    /*
     * 会议时长
     */
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
    private List<JoinUserDto> meetingAttendee;
}
