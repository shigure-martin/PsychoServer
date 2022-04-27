package com.psychoServer.controller;

import com.psychoServer.entity.CounselInfo;
import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.CounselInfoRepository;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.response.BaseResponse;
import com.psychoServer.response.PageResponse;
import com.psychoServer.response.SuccessResponse;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.psychoServer.service.CounselInfoService;
import com.psychoServer.service.CounselorInfoService;
import com.psychoServer.service.SupervisorInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lli.chen
 */
@RestController
@RequestMapping("/api/counsel")
@Api(value = "/api/counsel",tags = "咨询记录相关接口")
public class CounselInfoController extends BaseController {
    @Autowired
    private CounselInfoService counselInfoService;

    @Autowired
    private CounselorInfoService counselorInfoService;

    @Autowired
    private SupervisorInfoService supervisorInfoService;

    @Autowired
    private CounselInfoRepository counselInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建咨询记录")
    public BaseResponse create(@RequestBody CounselInfo counselInfo) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        counselInfo.setStartTime(timestamp);
        return new SuccessResponse<>(counselInfoService.saveOrUpdate(counselInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id获取咨询记录")
    public BaseResponse getOne(@PathVariable Long id) {
        CounselInfo counselInfo = counselInfoService.getById(id);
        return new SuccessResponse<>(counselInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取咨询记录")
    public BaseResponse getList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = Integer_MAX_VALUE) int size,
                                @RequestParam(required = false) String searchCondition
    ) {
        List<OrderRequest> order = null;
        Pageable pageable = new PageRequest(page,size);
        List<CounselInfo> counselInfos = counselInfoService.getCounselInfos(page,size,order);
        if (!Strings.isNullOrEmpty(searchCondition)) {
            counselInfos = counselInfos.stream().filter(counselInfo -> counselInfo.toString().contains(searchCondition)).collect(Collectors.toList());
        }
        return new SuccessResponse<>(PageResponse.build(counselInfos, pageable));
    }

    @PutMapping
    @ApiOperation(value = "更新咨询记录")
    public BaseResponse update(@RequestBody CounselInfo counselInfo) {
        CounselInfo old = counselInfoService.getById(counselInfo.getId());
        Preconditions.checkNotNull(old);
        return new SuccessResponse<>(counselInfoService.saveOrUpdate(counselInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除咨询记录")
    public BaseResponse delete(@PathVariable Long id) {
        return new SuccessResponse<>(counselInfoService.deleteEntity(id));
    }

    @GetMapping("/todayAll")
    @ApiOperation(value = "获取一日的咨询统计(分时)")
    public BaseResponse getTodayAll() {
        //CounselInfo counselInfo
        return new SuccessResponse(counselInfoService.statisticOneDay());
    }

    @GetMapping("/todaySum")
    @ApiOperation(value = "获取一日的咨询统计(总和)")
    public BaseResponse getTodayStatistic() {
        //CounselInfo counselInfo
        return new SuccessResponse(counselInfoService.sumOneDay());
    }

    @GetMapping("/weekAll")
    @ApiOperation(value = "获取七日的咨询统计")
    public BaseResponse getWeekAll() {
        //CounselInfo counselInfo
        return new SuccessResponse(counselInfoService.statisticOneWeek());
    }

    @GetMapping("/counselor")
    @ApiOperation(value = "获取咨询师咨询记录")
    public BaseResponse getCounselor(@RequestParam Long id) {
        CounselorInfo counselorInfo = counselorInfoService.getById(id);
        Preconditions.checkNotNull(counselorInfo, "不存在该咨询师");
        return new SuccessResponse(counselInfoService.getByCounselorId(counselorInfo.getId()));
    }

    @GetMapping("/supervisor")
    @ApiOperation(value = "获取督导咨询记录")
    public BaseResponse getSupervisor(@RequestParam Long id) {
        SupervisorInfo supervisorInfo = supervisorInfoService.getById(id);
        Preconditions.checkNotNull(supervisorInfo, "不存在该咨询师");
        return new SuccessResponse(counselInfoService.getBySupervisorId(supervisorInfo.getId()));
    }
}
