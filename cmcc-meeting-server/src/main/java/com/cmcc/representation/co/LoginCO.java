package com.cmcc.representation.co;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 获取token
 * @author baiyanmin
 * @since 2020-09-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginCO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "非法请求")
    private String identity;

    @NotEmpty(message = "key不能为空")
    private String key;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "密码不能为空")
    private String password;

}
