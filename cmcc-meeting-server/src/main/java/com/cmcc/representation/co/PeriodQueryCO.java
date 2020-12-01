package com.cmcc.representation.co;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 周期列表
 * @author baiyanmin
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PeriodQueryCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 会议状态
     */
    private String meetingStatus;



}
