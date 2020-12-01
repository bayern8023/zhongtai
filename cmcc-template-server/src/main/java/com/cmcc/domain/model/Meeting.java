package com.cmcc.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String pmUuid;

    private Long meetingId;

    private String meetingTheme;

    private String meetingTime;

    private String meetingDate;

    private String meetingStatus;

    private String meetingIfmute;

    private int meetingLength;

    private String meetingType;

    private String createTime;
}
