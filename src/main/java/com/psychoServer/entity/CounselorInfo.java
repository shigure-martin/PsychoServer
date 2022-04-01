package com.psychoServer.entity;

import com.psychoServer.constants.Gender;
import com.psychoServer.constants.WorkStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@ApiModel(value = "咨询师信息")
public class CounselorInfo extends IEntity {
    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("身份证号码")
    private String idCardNum;

    @ApiModelProperty("性别")
    private Gender gender;

    @ApiModelProperty("年龄")
    private Integer counselorAge;

    @ApiModelProperty("账号id")
    private Long accountId;

    @ApiModelProperty(value = "咨询师姓名")
    private String counselorName;

    @ApiModelProperty(value = "咨询师联系方式")
    private String phoneNum;

    @ApiModelProperty(value = "评价分数")
    private Double evaluateScore = 0.0;

    @ApiModelProperty(value = "排班日期（缓存一个月的")
    @ElementCollection
    private Set<Date> scheduleDates;

    @ApiModelProperty(value = "今日咨询次数")
    private Integer counselToday;

    @ApiModelProperty(value = "咨询次数")
    private Integer counselNum = 0;

    @ApiModelProperty(value = "今日咨询时长")
    private Long counselTime;

    @ApiModelProperty(value = "咨询师状态")
    private WorkStatus workStatus = WorkStatus.left;

    @ElementCollection
    @ApiModelProperty(value = "绑定督导id")
    private Set<Long> supervisorIds;

    @ApiModelProperty(value = "咨询记录")
    @OneToMany
    @JoinColumn(referencedColumnName = "id", name = "counselorId", foreignKey = @ForeignKey(name = "null", value = ConstraintMode
            .NO_CONSTRAINT),insertable = false, updatable = false)
    private Set<CounselInfo> counselInfos;
}
