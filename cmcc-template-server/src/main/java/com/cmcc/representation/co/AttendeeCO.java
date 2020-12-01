package com.cmcc.representation.co;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AttendeeCO implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
        参会人
     */
    private String mobile;
    /*
        参会人名字
     */
    private String name;

    /*
        会议类型
     */
    private String business;

    /*
        是否高清会议
     */
    @JsonProperty(value = "is_volte")
    private boolean isVolte;

}

