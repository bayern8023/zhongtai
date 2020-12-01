package com.cmcc.application.enums;

/**
 * @author baiyanmin
 * @description 会议状态枚举
 * @date 2020-09-18
 */
public enum MeetingStatusEnum {
    W("W", "未开始"),
    R("R", "运行中"),
    C("C", "收藏"),
    E("E", "已结束");

    private String k;
    private String v;

    MeetingStatusEnum(String k, String v) {
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
