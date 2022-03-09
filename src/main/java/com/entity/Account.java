package com.entity;

import com.constants.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.JPACryptoConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
public class Account extends IEntity {

    @ApiModelProperty(value = "用户名")
    @Column(unique = true, nullable = false)
    private String loginName;

    @ApiModelProperty(value = "密码")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Convert(converter = JPACryptoConverter.class)
    private String password = "123456";

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ApiModelProperty(value = "访客id")
    private Long customerId;

    @ApiModelProperty(value = "访客信息")
    @OneToOne
    @JoinColumn(name = "customerId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "null", value = ConstraintMode
            .NO_CONSTRAINT), insertable = false, updatable = false)
    @Where(clause = "deleted = 0")
    private CustomerInfo customerInfo;

    @ApiModelProperty(value = "咨询师id")
    private Long counselorId;

    @ApiModelProperty(value = "咨询师信息")
    @OneToOne
    @JoinColumn(name = "counselorId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "null", value = ConstraintMode
            .NO_CONSTRAINT), insertable = false, updatable = false)
    @Where(clause = "deleted = 0")
    private CounselorInfo counselorInfo;

    @ApiModelProperty(value = "督导id")
    private Long supervisorId;

    @ApiModelProperty(value = "督导信息")
    @OneToOne
    @JoinColumn(name = "supervisorId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "null", value = ConstraintMode
            .NO_CONSTRAINT), insertable = false, updatable = false)
    @Where(clause = "deleted = 0")
    private SupervisorInfo supervisorInfo;

    @JsonIgnore
    public String getAuthority() {
        return "ROLE_ACCOUNT";
    }
}

