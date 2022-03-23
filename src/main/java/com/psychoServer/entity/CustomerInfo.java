package com.psychoServer.entity;

import com.psychoServer.constants.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@ApiModel(value = "访客信息")
public class CustomerInfo extends IEntity {
    @ApiModelProperty("账号id")
    private Long accountId;

    @ApiModelProperty(value = "访客头像")
    private String customerImage;

    @ApiModelProperty(value = "访客昵称")
    private String nickName;

    @ApiModelProperty(value = "访客姓名")
    private String customerName;

    @ApiModelProperty(value = "访客年龄")
    private Integer customerAge;

    @ApiModelProperty(value = "访客性别")
    private Gender customerGender;

    @ApiModelProperty(value = "电话号码")
    private String phoneNum;

    @ApiModelProperty(value = "紧急联系人电话")
    private String emergencyPhone;

    @ApiModelProperty(value = "紧急联系人姓名")
    private String emergencyName;

    @ApiModelProperty(value = "咨询记录")
    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "customerId", insertable = false, updatable = false)
    private Set<CounselInfo> counselInfos;
}
