package com.psychoServer.service;

import com.psychoServer.constants.RoleType;
import com.psychoServer.constants.WorkStatus;
import com.psychoServer.entity.Account;
import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.repository.CounselorInfoRepository;
import com.psychoServer.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author lli.chen
 */
@Service
public class CounselorInfoService extends BasicService<CounselorInfo, Long> {

    private CounselorInfoRepository counselorInfoRepository;

    @Autowired
    private UserService userService;

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
            if (counselorInfo.getComCount() == 0)
                counselorInfo.setWorkStatus(WorkStatus.idle);
        }
        return saveOrUpdate(counselorInfo);
    }
}

