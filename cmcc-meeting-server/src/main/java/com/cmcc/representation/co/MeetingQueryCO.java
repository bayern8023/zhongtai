package com.cmcc.representation.co;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 周期会议信息表
 * @author baiyanmin
 * @since 2020-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MeetingQueryCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 会议uuid
     */
    private String uuid;

    /*
     * 会议名
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
     * 会议时间
     */
    private String meetingTime;

    /*
     * 周期类型
     */
    private String periodType;

    /*
     * 更新时间
     */
    private String updateTime;

    /*
     * 操作人
     */
    private String hostEcUid;

    /*
     * 操作人
     */
    private String operatorEcUid;

    /*
     * 会议静音
     */
    private String meetingIfmute;

    /*
     * 会议时长
     */
    private String meetingLength;


}
