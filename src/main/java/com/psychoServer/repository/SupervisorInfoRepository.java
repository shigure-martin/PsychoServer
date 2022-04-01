package com.psychoServer.repository;

import com.psychoServer.entity.SupervisorInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

/**
 * @author lli.chen
 */
public interface SupervisorInfoRepository extends PagingAndSortingRepository<SupervisorInfo, Long> {
    List<SupervisorInfo> findByDeleted(boolean deleted);
    List<SupervisorInfo> findByDeleted(boolean deleted, Sort sort);

    List<SupervisorInfo> findByIdInAndDeleted(Set<Long> ids, boolean deleted);
}
