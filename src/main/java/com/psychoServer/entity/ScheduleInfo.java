package com.psychoServer.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@ApiModel(value = "排班信息")
public class ScheduleInfo extends IEntity {
    private Date date;

    private String dayOfWeek;

    @ElementCollection
    private Set<Long> counselorIds;

    @ElementCollection
    private Set<Long> supervisorIds;
}
