package com.psychoServer.request;

import lombok.Data;

@Data
public class ScoreRankRequest {
    private String counselorName;
    private Long counselorId;
    private Double counselScore;
}
