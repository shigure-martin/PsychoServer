package com.psychoServer.repository;

import com.psychoServer.entity.CounselInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

/**
 * @author lli.chen
 */
public interface CounselInfoRepository extends PagingAndSortingRepository<CounselInfo, Long> {
    List<CounselInfo> findByDeleted(boolean deleted);
    List<CounselInfo> findByDeleted(boolean deleted, Sort sort);

    List<CounselInfo> findByCounselorIdAndDeleted(Long counselorId, boolean deleted);
    List<CounselInfo> findBySupervisorIdAndDeleted(Long supervisorId, boolean deleted);

    List<CounselInfo> findByStartTimeBetweenAndDeleted(Date start, Date end, boolean deleted);
}
