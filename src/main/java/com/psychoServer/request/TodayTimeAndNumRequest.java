package com.psychoServer.request;

import lombok.Data;


@Data
public class TodayTimeAndNumRequest {
    private String duringTime;

    private Integer counselNum;

    private Long counselTime;
}
