package com.psychoServer.request;

import lombok.Data;


@Data
public class CounselStatisticRequest {
    private String duringTime;
    private Long counselTime;
    private Integer counselNum;
}
