package com.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
public class CounselorInfo extends IEntity {
    private String counselorName;


}
