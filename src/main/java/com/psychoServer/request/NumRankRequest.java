package com.psychoServer.request;

import lombok.Data;

@Data
public class NumRankRequest {
    private String counselorName;
    private Long counselorId;
    private Integer counselNum;
}
