package com.psychoServer.request;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class OrderRequest {
    private Sort.Direction direction = Sort.Direction.ASC;
    private String property;
}
