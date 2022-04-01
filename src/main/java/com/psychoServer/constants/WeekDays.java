package com.psychoServer.constants;

public enum WeekDays {
    MON("星期一"), TUE("星期二"), WED("星期三"),
    THU("星期四"), FRI("星期五"), SAT("星期六"),
    SUN("星期日");

    public String day;

    WeekDays(String d) {
        this.day = d;
    }
}
