package com.cmcc.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Attendee implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String MEETING = "MEETING";

    private static final String TEMPLATE = "TEMPLATE";

    /*
        自增主键
     */
    private int id;
    /*
        参会人
     */
    private String mobile;
    /*
        从属与模版或者周期会议的uuid
     */
    private String uuid;
    /*
        参会人名字
     */
    private String name;

    /*
        类型，目前只有MEETING和TEMPLATE，标示来源于会议或者模板
     */
    private String type;

    /*
        会议类型
     */
    private String business;

    /*
        是否高清会议
     */
    private boolean isVolte;

    /**
     * 设置类型为meeting
     */
    public void setTypeMeeting() {
        this.type = MEETING;
    }

    /**
     * 设置类型为template
     */
    public void setTypeTemplate() {
        this.type = TEMPLATE;
    }

}

