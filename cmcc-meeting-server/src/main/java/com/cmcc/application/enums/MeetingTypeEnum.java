package com.cmcc.application.enums;

/**
 * @author baiyanmin
 * @description 会议类型枚举
 * @date 2020-09-12
 */
public enum MeetingTypeEnum {

    MEETING_TYPE_0("预约会议","0"),
    MEETING_TYPE_1("即时会议","1"),
    MEETING_TYPE_2("周期会议","2");

    private String k;
    private String v;

    MeetingTypeEnum(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

}
