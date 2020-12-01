package com.cmcc.domain.model;

import com.cmcc.representation.dto.JoinUserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 周期会议信息表
 * @author baiyanmin
 * @since 2020-09-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PeriodQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 周期uuid
     */
    private String uuid;

    /*
     * 会议状态
     */
    private String meetingStatus;

    /*
     * 操作人
     */
    private String operatorEcUid;

    /*
     * 会议标题
     */
    private String meetingTheme;

    /*
     * 页数
     */
    private int page;

    /*
     * 分页大小
     */
    private int pageSize;

    /*
     * 参会人周期列表
     */
    private List<Attendee> periodList;
}
