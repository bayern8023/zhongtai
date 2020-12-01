package com.cmcc.representation.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 周期会议信息表
 * @author baiyanmin
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PeriodDto implements Serializable {

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
     * 周期类型
     */
    private String periodType;

    /*
     * 周期时间
     */
    private String periodTime;

    /*
     * 周期会议下一个时间
     */
    private String meetingNextTime;

    /*
     * 周期会议状态
     */
    private String meetingStatus;

    /*
     * 周期操作人
     */
    private String operatorEcUid;

}
