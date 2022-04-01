package com.psychoServer.controller;

import com.psychoServer.entity.CounselInfo;
import com.psychoServer.entity.EvaluateInfo;
import com.psychoServer.repository.EvaluateInfoRepository;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.response.BaseResponse;
import com.psychoServer.response.PageResponse;
import com.psychoServer.response.SuccessResponse;
import com.psychoServer.service.CounselInfoService;
import com.psychoServer.service.EvaluateInfoService;
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
@RequestMapping("/api/evaluate")
@Api(value = "/api/evaluate",tags = "评价信息相关接口")
public class EvaluateInfoController extends BaseController {
    @Autowired
    private EvaluateInfoService evaluateInfoService;

    @Autowired
    private CounselInfoService counselInfoService;

    @Autowired
    private EvaluateInfoRepository evaluateInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建评价信息")
    public BaseResponse create(@RequestBody EvaluateInfo evaluateInfo) {
        Preconditions.checkNotNull(evaluateInfo.getCounselInfoId(), "咨询信息id不得为空");
        CounselInfo counselInfo = counselInfoService.getById(evaluateInfo.getCounselInfoId());
        Preconditions.checkNotNull(counselInfo,"不存在该咨询信息");
        return new SuccessResponse<>(evaluateInfoService.create(evaluateInfo, counselInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id获取评价信息")
    public BaseResponse getOne(@PathVariable Long id) {
        EvaluateInfo evaluateInfo = evaluateInfoService.getById(id);
        return new SuccessResponse<>(evaluateInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取评价信息")
    public BaseResponse getList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = Integer_MAX_VALUE) int size,
                                @RequestParam(required = false) String searchCondition
    ) {
        List<OrderRequest> order = null;
        Pageable pageable = new PageRequest(page,size);
        List<EvaluateInfo> evaluateInfos = evaluateInfoService.getEvaluateInfos(page,size,order);
        if (!Strings.isNullOrEmpty(searchCondition)) {
            evaluateInfos = evaluateInfos.stream().filter(evaluateInfo -> evaluateInfo.toString().contains(searchCondition)).collect(Collectors.toList());
        }
        return new SuccessResponse<>(PageResponse.build(evaluateInfos, pageable));
    }

    @PutMapping
    @ApiOperation(value = "更新评价信息")
    public BaseResponse update(@RequestBody EvaluateInfo evaluateInfo) {
        EvaluateInfo old = evaluateInfoService.getById(evaluateInfo.getId());
        Preconditions.checkNotNull(old);
        return new SuccessResponse<>(evaluateInfoService.saveOrUpdate(evaluateInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除评价信息")
    public BaseResponse delete(@PathVariable Long id) {
        return new SuccessResponse<>(evaluateInfoService.deleteEntity(id));
    }
}
