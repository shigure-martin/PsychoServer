package com.entity;

import com.constants.Gender;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
public class CustomerInfo extends IEntity {
    private String customerName;

    private Integer customerAge;

    private Gender customerGender;

    private String phoneNum;
}
