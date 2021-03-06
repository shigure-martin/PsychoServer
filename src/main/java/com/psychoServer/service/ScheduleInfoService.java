package com.psychoServer.service;

import com.psychoServer.constants.WeekDays;
import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.entity.ScheduleInfo;
import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.ScheduleInfoRepository;
import com.psychoServer.request.ModifyWeeklyRequest;
import com.psychoServer.request.OrderRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lli.chen
 */
@Service
public class ScheduleInfoService extends BasicService<ScheduleInfo, Long> {

    private ScheduleInfoRepository scheduleInfoRepository;

    @Autowired
    private CounselorInfoService counselorInfoService;

    @Autowired
    private SupervisorInfoService supervisorInfoService;

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
        //List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByDayOfWeekInAndDeleted(request.getWeekDaysList().stream().map(WeekDays::toString).collect(Collectors.toSet()), false);

        Date start = getMonthFirstDay();
        Date end = getMonthLastDat();

        List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByDateBetweenAndDeleted(start, end, false);

        for (ScheduleInfo scheduleInfo : scheduleInfos) {
            if (request.getWeekDaysList().toString().contains(scheduleInfo.getDayOfWeek())) {
                if (scheduleInfo.getCounselorIds() != null) {
                    scheduleInfo.getCounselorIds().add(request.getId());
                } else {
                    Set<Long> set = new HashSet<>();
                    set.add(request.getId());
                    scheduleInfo.setCounselorIds(set);
                }
            } else {
                if (scheduleInfo.getCounselorIds() != null && scheduleInfo.getCounselorIds().contains(request.getId())){
                    scheduleInfo.getCounselorIds().remove(request.getId());
                }
            }
        }
        saveOrUpdateAll(scheduleInfos);

        counselorInfo.setWeekSchedule(request.getWeekDaysList());
        counselorInfoService.saveOrUpdate(counselorInfo);

        return counselorInfo;
    }
    public SupervisorInfo modifyScheduleSupervisor(ModifyWeeklyRequest request, SupervisorInfo supervisorInfo){
        //List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByDayOfWeekInAndDeleted(request.getWeekDaysList().stream().map(WeekDays::toString).collect(Collectors.toSet()), false);
        Date start = getMonthFirstDay();
        Date end = getMonthLastDat();

        List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByDateBetweenAndDeleted(start, end, false);

        for (ScheduleInfo scheduleInfo:scheduleInfos){
            if (request.getWeekDaysList().toString().contains(scheduleInfo.getDayOfWeek())) {
                if (scheduleInfo.getSupervisorIds() != null){
                    scheduleInfo.getSupervisorIds().add(request.getId());
                }else{
                    Set<Long> set= new HashSet<>();
                    set.add(request.getId());
                    scheduleInfo.setSupervisorIds(set);
                }
            } else {
                if (scheduleInfo.getSupervisorIds() != null && scheduleInfo.getSupervisorIds().contains(request.getId())) {
                    scheduleInfo.getSupervisorIds().remove(request.getId());
                }
            }
        }
        saveOrUpdateAll(scheduleInfos);

        supervisorInfo.setWeekSchedule(request.getWeekDaysList());
        supervisorInfoService.saveOrUpdate(supervisorInfo);

        return supervisorInfo;
    }

    public ScheduleInfo getToday(){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.HOUR_OF_DAY, 0); //???
        cal.set(Calendar.MINUTE, 0); //???
        cal.set(Calendar.SECOND, 0); //???
        cal.set(Calendar.MILLISECOND, 0);
        Date startTime = cal.getTime();

        cal.set(Calendar.HOUR_OF_DAY, 23); //???
        cal.set(Calendar.MINUTE, 59); //???
        cal.set(Calendar.SECOND, 59); //???
        cal.set(Calendar.MILLISECOND, 0);
        Date endTime = cal.getTime();

        ScheduleInfo scheduleInfo = scheduleInfoRepository.findByDateAfterAndDateBeforeAndDeleted(startTime, endTime, false);
        return scheduleInfo;
    }

    public List<Date> getCounselorSchedule(Long id) {
        List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findByCounselorIdsContainsAndDeleted(id, false);
        return scheduleInfos.stream().map(ScheduleInfo::getDate).collect(Collectors.toList());
    }

    public List<Date> getSupervisorSchedule(Long id) {
        List<ScheduleInfo> scheduleInfos = scheduleInfoRepository.findBySupervisorIdsContainsAndDeleted(id, false);
        return scheduleInfos.stream().map(ScheduleInfo::getDate).collect(Collectors.toList());
    }

    public ScheduleInfo update(Long id, ScheduleInfo scheduleInfo) {
        ScheduleInfo old = getById(id);
        BeanUtils.copyProperties(scheduleInfo, old);
        return saveOrUpdate(old);
    }

    public Date getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date start = calendar.getTime();

        return start;
    }

    public Date getMonthLastDat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }
}
