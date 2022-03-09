package com.repository;

import com.entity.ScheduleInfo;
import com.entity.ScheduleInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author lli.chen
 */
public interface ScheduleInfoRepository extends PagingAndSortingRepository<ScheduleInfo, Long> {
    List<ScheduleInfo> findByDeleted(boolean deleted);
    List<ScheduleInfo> findByDeleted(boolean deleted, Sort sort);
}