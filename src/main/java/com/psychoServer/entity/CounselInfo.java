package com.psychoServer.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@ApiModel(value = "咨询记录信息")
public class CounselInfo extends IEntity {
    @ApiModelProperty(value = "咨询开始时间")
    private Timestamp startTime;

    @ApiModelProperty(value = "咨询结束时间")
    private Timestamp endTime;

    @ApiModelProperty(value = "咨询师ID")
    private Long counselorId;

    @ApiModelProperty(value = "访客ID")
    private Long customerId;

    @ApiModelProperty(value = "督导ID")
    private Long supervisorId;

    @ApiModelProperty(value = "历史会话消息")
    private String historyMessage;

    @ApiModelProperty(value = "咨询时长")
    private Long duration;

    @ApiModelProperty(value = "评价信息id")
    private Long evaluateId;

    @OneToOne
    @JoinColumn(name = "evaluateId", referencedColumnName = "id", foreignKey = @ForeignKey(name = "null", value = ConstraintMode
            .NO_CONSTRAINT), insertable = false, updatable = false)
    @ApiModelProperty(value = "评价信息")
    private EvaluateInfo evaluateInfo;

}
