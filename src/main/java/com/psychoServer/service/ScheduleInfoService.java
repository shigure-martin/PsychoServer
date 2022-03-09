package com.psychoServer.service;

import com.psychoServer.entity.ScheduleInfo;
import com.psychoServer.repository.ScheduleInfoRepository;
import com.psychoServer.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lli.chen
 */
@Service
public class ScheduleInfoService extends BasicService<ScheduleInfo, Long> {

    private ScheduleInfoRepository scheduleInfoRepository;

    @Autowired
    public ScheduleInfoService(ScheduleInfoRepository scheduleInfoRepository) {
        super(scheduleInfoRepository);
        this.scheduleInfoRepository = scheduleInfoRepository;
    }

    public List<ScheduleInfo> getScheduleInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new ScheduleInfo());
        List<ScheduleInfo> result = scheduleInfoRepository.findByDeleted(false,sort);
        return result;
    }
}
