package com.psychoServer.service;

import com.psychoServer.constants.RoleType;
import com.psychoServer.constants.WorkStatus;
import com.psychoServer.entity.Account;
import com.psychoServer.entity.CounselInfo;
import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.entity.EvaluateInfo;
import com.psychoServer.repository.CounselorInfoRepository;
import com.psychoServer.request.NumRankRequest;
import com.psychoServer.request.OrderRequest;
import com.psychoServer.request.ScoreRankRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author lli.chen
 */
@Service
public class CounselorInfoService extends BasicService<CounselorInfo, Long> {

    private CounselorInfoRepository counselorInfoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CounselInfoService counselInfoService;

    @Autowired
    public CounselorInfoService(CounselorInfoRepository counselorInfoRepository) {
        super(counselorInfoRepository);
        this.counselorInfoRepository = counselorInfoRepository;
    }

    public List<CounselorInfo> getCounselorInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new CounselorInfo());
        List<CounselorInfo> result = counselorInfoRepository.findByDeleted(false,sort);
        return result;
    }

    public CounselorInfo create(CounselorInfo counselorInfo) {
        CounselorInfo newOne = saveOrUpdate(counselorInfo);
        Account account = userService.getById(counselorInfo.getAccountId());
        if (account == null){
            return null;
        }
        account.setCounselorId(newOne.getId());
        account.setRoleType(RoleType.counselor);
        userService.saveOrUpdate(account);
        return newOne;
    }

    public List<CounselorInfo> getByIds(Set<Long> counselorIds) {
        return counselorInfoRepository.findByIdInAndDeleted(counselorIds, false);
    }

    public CounselorInfo combine (CounselorInfo counselorInfo, Long supervisorId) {
        if (counselorInfo.getSupervisorIds() != null) {
            counselorInfo.getSupervisorIds().add(supervisorId);
        } else {
            Set<Long> set = new HashSet<>();
            set.add(supervisorId);
            counselorInfo.setSupervisorIds(set);
        }
        return saveOrUpdate(counselorInfo);
    }

    public CounselorInfo comCountModify(CounselorInfo counselorInfo, Boolean isAdd) {
        if (isAdd) {
            counselorInfo.setComCount(counselorInfo.getComCount() + 1);
            counselorInfo.setWorkStatus(WorkStatus.busy);
        } else {
            counselorInfo.setComCount(counselorInfo.getComCount() - 1);
            if (counselorInfo.getComCount() <= 0)
                counselorInfo.setComCount(0L);
                counselorInfo.setWorkStatus(WorkStatus.idle);
        }
        return saveOrUpdate(counselorInfo);
    }

    public List<NumRankRequest> getNumRank() {
        List<NumRankRequest> result = new ArrayList<>();

        List<CounselInfo> counselInfos = counselInfoService.getCurrentMonth();
        Map<Long, List<CounselInfo>> map = counselInfos.stream().collect(Collectors.groupingBy(CounselInfo::getCounselorId));

        for (Map.Entry<Long, List<CounselInfo>> entry : map.entrySet()) {
            NumRankRequest request = new NumRankRequest();
            request.setCounselorId(entry.getKey());
            request.setCounselNum(entry.getValue().size());
            CounselorInfo counselorInfo = getById(entry.getKey());
            request.setCounselorName(counselorInfo.getCounselorName());

            result.add(request);
        }

        Collections.sort(result, (o1, o2) -> o2.getCounselNum().compareTo(o1.getCounselNum()));
        return result;
    }

    public List<ScoreRankRequest> getScoreRank() {
        List<ScoreRankRequest> result = new ArrayList<>();

        List<CounselInfo> counselInfos = counselInfoService.getCurrentMonth();
        Map<Long, List<EvaluateInfo>> map = counselInfos.stream().collect(Collectors.groupingBy(CounselInfo::getCounselorId, Collectors.mapping(CounselInfo::getEvaluateInfo, Collectors.toList())));

        for (Map.Entry<Long, List<EvaluateInfo>> entry : map.entrySet()) {
            ScoreRankRequest request = new ScoreRankRequest();
            request.setCounselorId(entry.getKey());
            request.setCounselScore(entry.getValue().stream().mapToDouble(EvaluateInfo::getStarToCounselor).sum() / entry.getValue().size());

            CounselorInfo counselorInfo = getById(entry.getKey());
            request.setCounselorName(counselorInfo.getCounselorName());

            result.add(request);
        }

        return result;
    }
}

