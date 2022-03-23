package com.psychoServer.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@ApiModel(value = "督导信息")
public class SupervisorInfo extends IEntity {
    @ApiModelProperty("账号id")
    private Long accountId;

    @ApiModelProperty(value = "督导姓名")
    private String supervisorName;

    @ApiModelProperty(value = "联系电话")
    private String phoneNum;

    @ApiModelProperty(value = "今日咨询次数")
    private Integer counselToday;

    @ApiModelProperty(value = "今日咨询时长")
    private Long counselTime;

    @ApiModelProperty(value = "绑定咨询师id")
    @ElementCollection
    private Set<Long> counselorIds;
}
