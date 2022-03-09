package com.controller;

import com.controller.BaseController;
import com.entity.CounselorInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.repository.CounselorInfoRepository;
import com.request.OrderRequest;
import com.response.BaseResponse;
import com.response.PageResponse;
import com.response.SuccessResponse;
import com.service.CounselorInfoService;
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
@RequestMapping("/api/counselor")
@Api(value = "/api/counselor",tags = "咨询师信息相关接口")
public class CounselorInfoController extends BaseController {
    @Autowired
    private CounselorInfoService counselorInfoService;

    @Autowired
    private CounselorInfoRepository counselorInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建咨询师信息")
    public BaseResponse create(@RequestBody CounselorInfo counselorInfo) {
        return new SuccessResponse<>(counselorInfoService.saveOrUpdate(counselorInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id获取咨询师信息")
    public BaseResponse getOne(@PathVariable Long id) {
        CounselorInfo counselorInfo = counselorInfoService.getById(id);
        return new SuccessResponse<>(counselorInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取咨询师信息")
    public BaseResponse getList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = Integer_MAX_VALUE) int size,
                                @RequestParam(required = false) String searchCondition
    ) {
        List<OrderRequest> order = null;
        Pageable pageable = new PageRequest(page,size);
        List<CounselorInfo> counselorInfos = counselorInfoService.getCounselorInfos(page,size,order);
        if (!Strings.isNullOrEmpty(searchCondition)) {
            counselorInfos = counselorInfos.stream().filter(counselorInfo -> counselorInfo.toString().contains(searchCondition)).collect(Collectors.toList());
        }
        return new SuccessResponse<>(PageResponse.build(counselorInfos, pageable));
    }

    @PutMapping
    @ApiOperation(value = "更新咨询师信息")
    public BaseResponse update(@RequestBody CounselorInfo counselorInfo) {
        CounselorInfo old = counselorInfoService.getById(counselorInfo.getId());
        Preconditions.checkNotNull(old);
        return new SuccessResponse<>(counselorInfoService.saveOrUpdate(counselorInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除咨询师信息")
    public BaseResponse delete(@PathVariable Long id) {
        return new SuccessResponse<>(counselorInfoService.deleteEntity(id));
    }
}
