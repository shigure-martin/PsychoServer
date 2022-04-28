package com.psychoServer.request;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CombineRequest {
    private Set<Long> counselorIds;
    private Set<Long> supervisorIds;
}
