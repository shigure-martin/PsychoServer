package com.service;

import com.entity.CustomerInfo;
import com.repository.CustomerInfoRepository;
import com.request.OrderRequest;
import com.entity.CustomerInfo;
import com.repository.CustomerInfoRepository;
import com.request.OrderRequest;
import com.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lli.chen
 */
@Service
public class CustomerInfoService extends BasicService<CustomerInfo, Long> {

    private CustomerInfoRepository customerInfoRepository;

    @Autowired
    public CustomerInfoService(CustomerInfoRepository customerInfoRepository) {
        super(customerInfoRepository);
        this.customerInfoRepository = customerInfoRepository;
    }

    public List<CustomerInfo> getCustomerInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new CustomerInfo());
        List<CustomerInfo> result = customerInfoRepository.findByDeleted(false,sort);
        return result;
    }
}
