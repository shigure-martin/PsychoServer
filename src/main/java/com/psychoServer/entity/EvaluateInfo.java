package com.psychoServer.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
@ApiModel(value = "评价信息")
public class EvaluateInfo extends IEntity {
    @ApiModelProperty(value = "咨询信息")
    private Long counselInfoId;

    @ApiModelProperty(value = "咨询师评价（访客的）星级")
    private Double starToCustomer;

    @ApiModelProperty(value = "访客评价（咨询师的）星级")
    private Double starToCounselor;

    @ApiModelProperty(value = "咨询师评价（访客的）内容")
    private String infoToCustomer;

    @ApiModelProperty(value = "访客评价（咨询师的）内容")
    private String infoToCounselor;
}
