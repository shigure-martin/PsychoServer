package com.entity;

import com.constants.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.security.JPACryptoConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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

    @JsonIgnore
    public String getAuthority() {
        return "ROLE_ACCOUNT";
    }
}

