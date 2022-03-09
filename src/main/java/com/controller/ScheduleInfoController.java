package com.controller;

import com.controller.BaseController;
import com.entity.ScheduleInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.repository.ScheduleInfoRepository;
import com.request.OrderRequest;
import com.response.BaseResponse;
import com.response.PageResponse;
import com.response.SuccessResponse;
import com.service.ScheduleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lli.chen
 */
@RestController
@RequestMapping("/api/schedule")
@Api(value = "/api/schedule",tags = "排班信息相关接口")
public class ScheduleInfoController extends BaseController {
    @Autowired
    private ScheduleInfoService scheduleInfoService;

    @Autowired
    private ScheduleInfoRepository scheduleInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建排班信息")
    public BaseResponse create(@RequestBody ScheduleInfo scheduleInfo) {
        return new SuccessResponse<>(scheduleInfoService.saveOrUpdate(scheduleInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id获取排班信息")
    public BaseResponse getOne(@PathVariable Long id) {
        ScheduleInfo scheduleInfo = scheduleInfoService.getById(id);
        return new SuccessResponse<>(scheduleInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取排班信息")
    public BaseResponse getList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = Integer_MAX_VALUE) int size,
                                @RequestParam(required = false) String searchCondition
    ) {
        List<OrderRequest> order = null;
        Pageable pageable = new PageRequest(page,size);
        List<ScheduleInfo> scheduleInfos = scheduleInfoService.getScheduleInfos(page,size,order);
        if (!Strings.isNullOrEmpty(searchCondition)) {
            scheduleInfos = scheduleInfos.stream().filter(scheduleInfo -> scheduleInfo.toString().contains(searchCondition)).collect(Collectors.toList());
        }
        return new SuccessResponse<>(PageResponse.build(scheduleInfos, pageable));
    }

    @PutMapping
    @ApiOperation(value = "更新排班信息")
    public BaseResponse update(@RequestBody ScheduleInfo scheduleInfo) {
        ScheduleInfo old = scheduleInfoService.getById(scheduleInfo.getId());
        Preconditions.checkNotNull(old);
        return new SuccessResponse<>(scheduleInfoService.saveOrUpdate(scheduleInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除排班信息")
    public BaseResponse delete(@PathVariable Long id) {
        return new SuccessResponse<>(scheduleInfoService.deleteEntity(id));
    }
}
