package com.cmcc.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PeriodMeeting implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String meetingTheme;

    private String templateUuid;

    private String periodStarttime;

    private String periodEndtime;

    private String meetingDate;

    private String meetingTime;

    private String periodType;

    private String hostEcUid;

    private String operatorEcUid;

    private String meetingIfmute;

    private Double meetingLength;

    private String createTime;

    private String updateTime;
}
