package com.cmcc.representation.co;

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
public class JoinUserCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * 参会人手机
     */
    private String mobile;

    /*
     * 业务状态
     */
    private String is_volte;

    /*
     * 参会人业务类型
     */
    private String business;

    /*
     * 参会人名
     */
    private String name;

}
