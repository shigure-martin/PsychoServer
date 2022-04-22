package com.psychoServer.controller;

import com.psychoServer.entity.CounselInfo;
import com.psychoServer.repository.CounselInfoRepository;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.response.BaseResponse;
import com.psychoServer.response.PageResponse;
import com.psychoServer.response.SuccessResponse;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.psychoServer.service.CounselInfoService;
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
    public BaseResponse getTodayAll() {
        //CounselInfo counselInfo
        return null;
    }
}
