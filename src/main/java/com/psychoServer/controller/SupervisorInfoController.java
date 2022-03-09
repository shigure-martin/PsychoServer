package com.psychoServer.controller;

import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.SupervisorInfoRepository;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.response.BaseResponse;
import com.psychoServer.response.PageResponse;
import com.psychoServer.response.SuccessResponse;
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
    private SupervisorInfoRepository supervisorInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建督导信息")
    public BaseResponse create(@RequestBody SupervisorInfo supervisorInfo) {
        return new SuccessResponse<>(supervisorInfoService.saveOrUpdate(supervisorInfo));
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
}
