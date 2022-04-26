package com.psychoServer.controller;

import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.SupervisorInfoRepository;
import com.psychoServer.request.CombineRequest;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.response.BaseResponse;
import com.psychoServer.response.ErrorResponse;
import com.psychoServer.response.PageResponse;
import com.psychoServer.response.SuccessResponse;
import com.psychoServer.service.CounselorInfoService;
import com.psychoServer.service.SupervisorInfoService;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lli.chen
 */
@RestController
@RequestMapping("/api/supervisor")
@Api(value = "/api/supervisor",tags = "督导信息相关接口")
public class SupervisorInfoController extends BaseController {
    @Autowired
    private SupervisorInfoService supervisorInfoService;

    @Autowired
    private CounselorInfoService counselorInfoService;

    @Autowired
    private SupervisorInfoRepository supervisorInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建督导信息")
    public BaseResponse create(@RequestBody SupervisorInfo supervisorInfo) {
        if (supervisorInfo.getAccountId() == 0 || supervisorInfo.getAccountId() == null) {
            return new ErrorResponse("账号id不得为空或0");
        }
        return new SuccessResponse<>(supervisorInfoService.create(supervisorInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id获取督导信息")
    public BaseResponse getOne(@PathVariable Long id) {
        SupervisorInfo supervisorInfo = supervisorInfoService.getById(id);
        return new SuccessResponse<>(supervisorInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取督导信息")
    public BaseResponse getList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = Integer_MAX_VALUE) int size,
                                @RequestParam(required = false) String searchCondition
    ) {
        List<OrderRequest> order = null;
        Pageable pageable = new PageRequest(page,size);
        List<SupervisorInfo> supervisorInfos = supervisorInfoService.getSupervisorInfos(page,size,order);
        if (!Strings.isNullOrEmpty(searchCondition)) {
            supervisorInfos = supervisorInfos.stream().filter(supervisorInfo -> supervisorInfo.toString().contains(searchCondition)).collect(Collectors.toList());
        }
        return new SuccessResponse<>(PageResponse.build(supervisorInfos, pageable));
    }

    @PutMapping
    @ApiOperation(value = "更新督导信息")
    public BaseResponse update(@RequestBody SupervisorInfo supervisorInfo) {
        SupervisorInfo old = supervisorInfoService.getById(supervisorInfo.getId());
        Preconditions.checkNotNull(old);
        return new SuccessResponse<>(supervisorInfoService.saveOrUpdate(supervisorInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除督导信息")
    public BaseResponse delete(@PathVariable Long id) {
        return new SuccessResponse<>(supervisorInfoService.deleteEntity(id));
    }

    @PutMapping("/combine")
    @ApiOperation(value = "绑定咨询师")
    public BaseResponse combineCounselor(@RequestBody CombineRequest combineRequest) {
        SupervisorInfo supervisorInfo = supervisorInfoService.getById(combineRequest.getSupervisorId());
        Preconditions.checkNotNull(supervisorInfo,"该督导不存在");
        CounselorInfo counselorInfo = counselorInfoService.getById(combineRequest.getCounselorId());
        Preconditions.checkNotNull(counselorInfo, "不存在该咨询师");
        counselorInfoService.combine(counselorInfo, supervisorInfo.getId());
        return new SuccessResponse(supervisorInfoService.combine(supervisorInfo, counselorInfo.getId()));
    }

    @PostMapping("/more")
    @ApiOperation(value = "根据多个id获取督导信息")
    public BaseResponse getMore(@RequestBody Set<Long> supervisorIds) {
        if (supervisorIds.isEmpty() || supervisorIds == null)
            return new ErrorResponse("督导id为空/不存在");
        return new SuccessResponse(supervisorInfoService.getByIds(supervisorIds));
    }
}
