package com.psychoServer.repository;

import com.psychoServer.entity.EvaluateInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author lli.chen
 */
public interface EvaluateInfoRepository extends PagingAndSortingRepository<EvaluateInfo, Long> {
    List<EvaluateInfo> findByDeleted(boolean deleted);
    List<EvaluateInfo> findByDeleted(boolean deleted, Sort sort);
}
