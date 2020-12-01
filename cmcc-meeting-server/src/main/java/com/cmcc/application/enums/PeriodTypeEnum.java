package com.cmcc.application.enums;

/**
 * @author baiyanmin
 * @description 周期类型枚举
 * @date 2020-09-21
 */
public enum PeriodTypeEnum {

    MEETING_TYPE_0("天","DAY"),
    MEETING_TYPE_1("周","WEEK"),
    MEETING_TYPE_2("月","MONTH");

    private String k;
    private String v;

    PeriodTypeEnum(String k, String v) {
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
