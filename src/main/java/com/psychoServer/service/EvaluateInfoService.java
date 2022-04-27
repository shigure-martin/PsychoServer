package com.psychoServer.service;

import com.psychoServer.entity.CounselInfo;
import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.entity.EvaluateInfo;
import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.EvaluateInfoRepository;
import com.psychoServer.request.OrderRequest;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lli.chen
 */
@Service
public class EvaluateInfoService extends BasicService<EvaluateInfo, Long> {

    private EvaluateInfoRepository evaluateInfoRepository;

    @Autowired
    private CounselorInfoService counselorInfoService;

    @Autowired
    private CounselInfoService counselInfoService;

    @Autowired
    private SupervisorInfoService supervisorInfoService;

    @Autowired
    public EvaluateInfoService(EvaluateInfoRepository evaluateInfoRepository) {
        super(evaluateInfoRepository);
        this.evaluateInfoRepository = evaluateInfoRepository;
    }

    public List<EvaluateInfo> getEvaluateInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new EvaluateInfo());
        List<EvaluateInfo> result = evaluateInfoRepository.findByDeleted(false, sort);
        return result;
    }

    public EvaluateInfo create(EvaluateInfo evaluateInfo, CounselInfo counselInfo) {
        saveOrUpdate(evaluateInfo);
        counselInfo.setEvaluateId(evaluateInfo.getId());
        counselInfoService.saveOrUpdate(counselInfo);
        CounselorInfo counselorInfo = counselorInfoService.getById(counselInfo.getCounselorId());
        counselorInfo.setCounselNum(counselorInfo.getCounselNum() + 1);
        counselorInfo.setCounselTime(counselorInfo.getCounselTime() + counselInfo.getDuration());
        counselorInfo.setCounselToday(counselorInfo.getCounselToday() + 1);
        counselorInfo.setEvaluateScore(counselorInfo.getEvaluateScore() + evaluateInfo.getStarToCounselor());
        counselorInfoService.saveOrUpdate(counselorInfo);

        if (counselInfo.getSupervisorId() != null) {
            SupervisorInfo supervisorInfo = supervisorInfoService.getById(counselInfo.getSupervisorId());
            supervisorInfo.setCounselToday(supervisorInfo.getCounselToday() + 1);
            supervisorInfo.setCounselTime(supervisorInfo.getCounselTime() + counselInfo.getDuration());
            supervisorInfo.setTotalCounselNum(supervisorInfo.getTotalCounselNum() + 1);
            supervisorInfo.setTotalCounselTime(supervisorInfo.getTotalCounselTime() + counselInfo.getDuration());

            supervisorInfoService.saveOrUpdate(supervisorInfo);
        }

        if (counselInfo.getSupervisorId() != null) {
            SupervisorInfo supervisorInfo = supervisorInfoService.getById(counselInfo.getSupervisorId());
            if (supervisorInfo != null) {
                supervisorInfo.setCounselToday(supervisorInfo.getCounselToday() + 1);
                supervisorInfo.setCounselTime(supervisorInfo.getCounselTime() + counselInfo.getDuration());
                supervisorInfoService.saveOrUpdate(supervisorInfo);
            }
        }

        return evaluateInfo;
    }
}
