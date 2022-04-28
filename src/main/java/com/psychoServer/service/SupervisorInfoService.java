package com.psychoServer.service;

import com.psychoServer.constants.RoleType;
import com.psychoServer.entity.Account;
import com.psychoServer.entity.CounselInfo;
import com.psychoServer.entity.CounselorInfo;
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
    private CounselorInfoService counselorInfoService;

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

    public SupervisorInfo combine(SupervisorInfo supervisorInfo, Set<Long> counselorIds) {

        List<CounselorInfo> counselorInfos = counselorInfoService.getNotDeleted();
        for (CounselorInfo counselorInfo : counselorInfos) {

            if (counselorIds.contains(counselorInfo.getId())){

                if(counselorInfo.getSupervisorIds() != null) {
                    counselorInfo.getSupervisorIds().add(supervisorInfo.getId());
                }
                else {
                    Set<Long> set = new HashSet<>();
                    set.add(supervisorInfo.getId());
                    counselorInfo.setSupervisorIds(set);
                }

            }
            else {
                if(counselorInfo.getSupervisorIds() != null) {
                    counselorInfo.getSupervisorIds().remove(supervisorInfo.getId());
                }

            }
            counselorInfoService.saveOrUpdate(counselorInfo);
        }


        if (supervisorInfo.getCounselorIds() != null) {
            supervisorInfo.setCounselorIds(counselorIds);
        } else {
            Set<Long> set = new HashSet<>();
            set.addAll(counselorIds);
            supervisorInfo.setCounselorIds(set);
        }
        return saveOrUpdate(supervisorInfo);
    }

    public List<SupervisorInfo> getNotDeleted() {

        return supervisorInfoRepository.findByDeleted(false);
    }
}
