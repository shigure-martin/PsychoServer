package com.psychoServer.entity;

import com.psychoServer.constants.Gender;
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
    private Integer counselToday = 0;

    @ApiModelProperty(value = "今日咨询时长")
    private Long counselTime = 0L;

    @ApiModelProperty(value = "绑定咨询师id")
    @ElementCollection
    private Set<Long> counselorIds;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("身份证号码")
    private String idCardNum;

    @ApiModelProperty("性别")
    private Gender gender;

    @ApiModelProperty("年龄")
    private Integer counselorAge;

    @ApiModelProperty("督导资质")
    private String qualification;

    @ApiModelProperty("督导资质编号")
    private String qualificationNum;
}
