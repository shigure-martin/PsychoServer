package com.psychoServer.controller;

import com.psychoServer.entity.CustomerInfo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.psychoServer.repository.CustomerInfoRepository;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.response.BaseResponse;
import com.psychoServer.response.PageResponse;
import com.psychoServer.response.SuccessResponse;
import com.psychoServer.service.CustomerInfoService;
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
@RequestMapping("/api/customer")
@Api(value = "/api/customer",tags = "访客信息相关接口")
public class CustomerInfoController extends BaseController {
    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @PostMapping
    @ApiOperation(value = "新建访客信息")
    public BaseResponse create(@RequestBody CustomerInfo customerInfo) {
        return new SuccessResponse<>(customerInfoService.saveOrUpdate(customerInfo));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据Id获取访客信息")
    public BaseResponse getOne(@PathVariable Long id) {
        CustomerInfo customerInfo = customerInfoService.getById(id);
        return new SuccessResponse<>(customerInfo);
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页获取访客信息")
    public BaseResponse getList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = Integer_MAX_VALUE) int size,
                                @RequestParam(required = false) String searchCondition
    ) {
        List<OrderRequest> order = null;
        Pageable pageable = new PageRequest(page,size);
        List<CustomerInfo> customerInfos = customerInfoService.getCustomerInfos(page,size,order);
        if (!Strings.isNullOrEmpty(searchCondition)) {
            customerInfos = customerInfos.stream().filter(customerInfo -> customerInfo.toString().contains(searchCondition)).collect(Collectors.toList());
        }
        return new SuccessResponse<>(PageResponse.build(customerInfos, pageable));
    }

    @PutMapping
    @ApiOperation(value = "更新访客信息")
    public BaseResponse update(@RequestBody CustomerInfo customerInfo) {
        CustomerInfo old = customerInfoService.getById(customerInfo.getId());
        Preconditions.checkNotNull(old);
        return new SuccessResponse<>(customerInfoService.saveOrUpdate(customerInfo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据Id删除访客信息")
    public BaseResponse delete(@PathVariable Long id) {
        return new SuccessResponse<>(customerInfoService.deleteEntity(id));
    }
}
