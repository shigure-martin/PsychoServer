package com.psychoServer.repository;

import com.psychoServer.entity.CustomerInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author lli.chen
 */
public interface CustomerInfoRepository extends PagingAndSortingRepository<CustomerInfo, Long> {
    List<CustomerInfo> findByDeleted(boolean deleted);
    List<CustomerInfo> findByDeleted(boolean deleted, Sort sort);
}
