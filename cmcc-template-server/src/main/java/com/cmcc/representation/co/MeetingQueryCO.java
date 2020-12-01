package com.cmcc.representation.co;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 会议信息表
 * @author baiyanmin
 * @since 2020-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MeetingQueryCO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parentId;

    private String name;

}
