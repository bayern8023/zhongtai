package com.cmcc.application.enums;

/**
 * @author baiyanmin
 * @description 会议类型枚举
 * @date 2020-09-12
 */
public enum MeetingTypeEnum {
    METERING_1(1, "即时会议"),
    METERING_2(2, "周期会议"),
    METERING_3(3, "预约会议");

    private Integer k;
    private String v;

    MeetingTypeEnum(Integer k, String v) {
        this.k = k;
        this.v = v;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

}
