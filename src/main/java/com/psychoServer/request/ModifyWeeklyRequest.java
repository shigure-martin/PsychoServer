package com.psychoServer.request;

import com.psychoServer.constants.WeekDays;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

@Data
public class ModifyWeeklyRequest {
    @ApiModelProperty(value = "每周几？")
    private Set<WeekDays> weekDaysList;

    @ApiModelProperty(value = "督导/咨询师id")
    private Long id;

    @ApiModelProperty(value = "是否为咨询师，默认为false。false则为督导")
    private Boolean isCounselor = false;
}
