package com.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
public class SupervisorInfo extends IEntity {
    private String supervisorName;
}
