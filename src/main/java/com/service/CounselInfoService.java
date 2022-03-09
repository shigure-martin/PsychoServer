package com.service;

import com.entity.CounselInfo;
import com.repository.CounselInfoRepository;
import com.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
