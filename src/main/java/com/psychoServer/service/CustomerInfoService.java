package com.psychoServer.service;

import com.psychoServer.constants.RoleType;
import com.psychoServer.entity.Account;
import com.psychoServer.entity.CustomerInfo;
import com.psychoServer.repository.CustomerInfoRepository;
import com.psychoServer.request.OrderRequest;
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
    private UserService userService;

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

    public CustomerInfo create(CustomerInfo customerInfo) {
        CustomerInfo newOne = saveOrUpdate(customerInfo);
        Account account = userService.getById(customerInfo.getAccountId());
        if (account == null){
            return null;
        }
        account.setCustomerId(newOne.getId());
        account.setRoleType(RoleType.customer);
        userService.saveOrUpdate(account);
        return newOne;
    }
}
