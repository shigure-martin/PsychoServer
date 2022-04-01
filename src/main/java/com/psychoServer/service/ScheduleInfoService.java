package com.psychoServer.service;

import com.psychoServer.constants.WeekDays;
import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.entity.ScheduleInfo;
import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.ScheduleInfoRepository;
import com.psychoServer.request.ModifyWeeklyRequest;
import com.psychoServer.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public CounselorInfo modifyScheduleCounselor(ModifyWeeklyRequest request, CounselorInfo counselorInfo) {
        List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByDayOfWeekInAndDeleted(request.getWeekDaysList().stream().map(WeekDays::toString).collect(Collectors.toSet()), false);
        for (ScheduleInfo scheduleInfo : scheduleInfos) {
            if (scheduleInfo.getCounselorIds() != null) {
                scheduleInfo.getCounselorIds().add(request.getId());
            } else {
                Set<Long> set = new HashSet<>();
                set.add(request.getId());
                scheduleInfo.setCounselorIds(set);
            }
        }
        saveOrUpdateAll(scheduleInfos);
        return counselorInfo;
    }
    public SupervisorInfo modifyScheduleSupervisor(ModifyWeeklyRequest request, SupervisorInfo supervisorInfo){
        List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByDayOfWeekInAndDeleted(request.getWeekDaysList().stream().map(WeekDays::toString).collect(Collectors.toSet()), false);
        for (ScheduleInfo scheduleInfo:scheduleInfos){
            if (scheduleInfo.getSupervisorIds()!=null){
                scheduleInfo.getSupervisorIds().add(request.getId());
            }else{
                Set<Long> set= new HashSet<>();
                set.add(request.getId());
                scheduleInfo.setCounselorIds(set);
            }
        }
        saveOrUpdateAll(scheduleInfos);
        return supervisorInfo;
    }
}
