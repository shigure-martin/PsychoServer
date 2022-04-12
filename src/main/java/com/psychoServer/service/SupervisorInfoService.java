package com.psychoServer.service;

import com.psychoServer.constants.RoleType;
import com.psychoServer.entity.Account;
import com.psychoServer.entity.SupervisorInfo;
import com.psychoServer.repository.SupervisorInfoRepository;
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
public class SupervisorInfoService extends BasicService<SupervisorInfo, Long> {

    private SupervisorInfoRepository supervisorInfoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public SupervisorInfoService(SupervisorInfoRepository supervisorInfoRepository) {
        super(supervisorInfoRepository);
        this.supervisorInfoRepository = supervisorInfoRepository;
    }

    public List<SupervisorInfo> getSupervisorInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new SupervisorInfo());
        List<SupervisorInfo> result = supervisorInfoRepository.findByDeleted(false,sort);
        return result;
    }

    public SupervisorInfo create(SupervisorInfo supervisorInfo) {
        SupervisorInfo newOne = saveOrUpdate(supervisorInfo);
        Account account = userService.getById(supervisorInfo.getAccountId());
        if (account == null){
            return null;
        }
        account.setSupervisorId(newOne.getId());
        account.setRoleType(RoleType.supervisor);
        userService.saveOrUpdate(account);
        return newOne;
    }

    public List<SupervisorInfo> getByIds(Set<Long> supervisorIds) {
        return supervisorInfoRepository.findByIdInAndDeleted(supervisorIds, false);
    }

    public SupervisorInfo combine(SupervisorInfo supervisorInfo, Long counselorId) {
        if (supervisorInfo.getCounselorIds() != null) {
            supervisorInfo.getCounselorIds().add(counselorId);
        } else {
            Set<Long> set = new HashSet<>();
            set.add(counselorId);
            supervisorInfo.setCounselorIds(set);
        }
        return saveOrUpdate(supervisorInfo);
    }
}
