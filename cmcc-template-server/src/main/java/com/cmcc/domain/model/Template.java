/* Copyright©2020-2020 China Mobile Communications Group Co.,Ltd. All rights reserved. */
package com.cmcc.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Template implements Serializable {
    private static final long serialVersionUID = 1L;
    /*
        uuid
     */
    private String uuid;
    /*
       模版名称
     */
    private String templateName;
    /*
      创建人
     */
    private String createPeople;
    /*
        预设会议主持人，默认和创建人一致
     */
    private String meetingHost;
    /*
      创建时间
     */
    private String createTime;
    /*
        更新时间
     */
    private String updateTime;
    /*
      是否静音
     */
    private boolean meetingIfmute;
    /*
        画面设置
     */
    private int imageType;
    /*
        是否删除
     */
    private boolean ifDelete;
}

