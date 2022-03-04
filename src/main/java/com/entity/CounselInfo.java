package com.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Data
@Entity
public class CounselInfo extends IEntity {
    private Timestamp startTime;

    private Timestamp endTime;

    private Long counselorId;

    private Long customerId;

    private String historyMessage;

    private Long duration;
}
