package com.repository;

import com.entity.SupervisorInfo;
import com.entity.SupervisorInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author lli.chen
 */
public interface SupervisorInfoRepository extends PagingAndSortingRepository<SupervisorInfo, Long> {
    List<SupervisorInfo> findByDeleted(boolean deleted);
    List<SupervisorInfo> findByDeleted(boolean deleted, Sort sort);
}
