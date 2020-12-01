package com.cmcc.representation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 会议参会人
 * @author baiyanmin
 * @since 2020-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class JoinUserDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 参会人手机号
     */
    private String mobile;

    /*
     * 参会人业务类型
     */
    private String business;

    /*
     * 参会人名
     */
    private String name;

}
