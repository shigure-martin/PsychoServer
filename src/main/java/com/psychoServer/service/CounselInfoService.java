package com.psychoServer.service;

import com.psychoServer.entity.CounselInfo;
import com.psychoServer.repository.CounselInfoRepository;
import com.psychoServer.request.CounselStatisticRequest;
import com.psychoServer.request.OrderRequest;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author lli.chen
 */
@Service
public class CounselInfoService extends BasicService<CounselInfo, Long> {

    private CounselInfoRepository counselInfoRepository;

    @Autowired
    public CounselInfoService(CounselInfoRepository counselInfoRepository) {
        super(counselInfoRepository);
        this.counselInfoRepository = counselInfoRepository;
    }

    public List<CounselInfo> getCounselInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new CounselInfo());
        List<CounselInfo> result = counselInfoRepository.findByDeleted(false,sort);
        return result;
    }

    public List<CounselInfo> getByCounselorId(Long counselorId) {
        return counselInfoRepository.findByCounselorIdAndDeleted(counselorId, false);
    }

    public List<CounselInfo> getBySupervisorId(Long supervisorId) {
        return counselInfoRepository.findBySupervisorIdAndDeleted(supervisorId, false);
    }

    public List<CounselStatisticRequest> statisticOneDay () {
        List<CounselStatisticRequest> list = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        for (int i = 0; i < 6; i++) {
            Date start = calendar.getTime();
            calendar.add(Calendar.HOUR, 4);
            Date end = calendar.getTime();

            CounselStatisticRequest request = new CounselStatisticRequest();

            List<CounselInfo> counselInfos = counselInfoRepository.findByStartTimeBetweenAndDeleted(start, end, false);
            request.setCounselTime(counselInfos.stream().mapToLong(CounselInfo::getDuration).sum());
            request.setCounselNum(counselInfos.size());
            request.setDuringTime(end.toString());

            list.add(request);
        }
        return list;
    }

    public List<CounselStatisticRequest> statisticOneWeek () {
        List<CounselStatisticRequest> list = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        for (int i = 0; i < 7; i++) {
            Date end = calendar.getTime();
            calendar.add(Calendar.DATE, -1);
            Date start = calendar.getTime();

            CounselStatisticRequest request = new CounselStatisticRequest();

            List<CounselInfo> counselInfos = counselInfoRepository.findByStartTimeBetweenAndDeleted(start, end, false);

            request.setCounselNum(counselInfos.size());
            request.setDuringTime(start.toString());

            list.add(request);
        }
        return list;
    }

    public List<CounselInfo> getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        Date end = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date start = calendar.getTime();

        List<CounselInfo> result = counselInfoRepository.findByStartTimeBetweenAndDeleted(start, end, false);
        return result;
    }
}
