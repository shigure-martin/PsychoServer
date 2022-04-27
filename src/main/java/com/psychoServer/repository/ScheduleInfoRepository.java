package com.psychoServer.repository;

import com.psychoServer.entity.ScheduleInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author lli.chen
 */
public interface ScheduleInfoRepository extends PagingAndSortingRepository<ScheduleInfo, Long> {
    List<ScheduleInfo> findByDeleted(boolean deleted);
    List<ScheduleInfo> findByDeleted(boolean deleted, Sort sort);

    List<ScheduleInfo> findByDayOfWeekInAndDeleted(Set<String> list, boolean delete);
    ScheduleInfo findByDateAfterAndDateBeforeAndDeleted(Date startTime, Date endTime, boolean delete);
    List<ScheduleInfo> findByCounselorIdsContainsAndDeleted(Long id, boolean delete);
    List<ScheduleInfo> findBySupervisorIdsContainsAndDeleted(Long id, boolean delete);

    List<ScheduleInfo> findByDateBetweenAndDeleted(Date start, Date end, boolean delete);
}
