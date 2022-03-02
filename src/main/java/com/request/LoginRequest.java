package com.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequest {
    @ApiModelProperty(value = "用户名 ")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
}
