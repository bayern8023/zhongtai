package com.cmcc.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Attendee {

    /*
        参会人手机号
     */
    private String mobile;
    /*
        从属与模版或者周期会议的uuid
     */
    private String uuid;

    /*
     * 类型
     */
    private String type;

    /*
     * 业务类型
     */
    private String business;

    /*
     * 参会人名
     */
    private String name;

}

