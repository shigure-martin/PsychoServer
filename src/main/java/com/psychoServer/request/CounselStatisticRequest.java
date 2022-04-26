package com.psychoServer.request;

import lombok.Data;


@Data
public class CounselStatisticRequest {
    private String duringTime;

    private Integer counselNum;

    private Long counselTime;
}
