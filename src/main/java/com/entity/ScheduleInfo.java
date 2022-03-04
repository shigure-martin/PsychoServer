package com.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Data
@Entity
public class ScheduleInfo extends IEntity {
    private Date date;

    @OneToMany
    private Set<CounselorInfo> counselorInfos;

    @OneToMany
    private Set<SupervisorInfo> supervisorInfos;
}
