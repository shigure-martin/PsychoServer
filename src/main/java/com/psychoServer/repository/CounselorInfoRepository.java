package com.psychoServer.repository;

import com.psychoServer.entity.CounselorInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @author lli.chen
 */
public interface CounselorInfoRepository extends PagingAndSortingRepository<CounselorInfo, Long> {
    List<CounselorInfo> findByDeleted(boolean deleted);
    List<CounselorInfo> findByDeleted(boolean deleted, Sort sort);
}
