package com.psychoServer.service;

import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.repository.CounselorInfoRepository;
import com.psychoServer.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lli.chen
 */
@Service
public class CounselorInfoService extends BasicService<CounselorInfo, Long> {

    private CounselorInfoRepository counselorInfoRepository;

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
}
