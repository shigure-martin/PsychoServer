package com.service;

import com.entity.EvaluateInfo;
import com.repository.EvaluateInfoRepository;
import com.request.OrderRequest;
import com.entity.EvaluateInfo;
import com.repository.EvaluateInfoRepository;
import com.request.OrderRequest;
import com.service.BasicService;
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
    public EvaluateInfoService(EvaluateInfoRepository evaluateInfoRepository) {
        super(evaluateInfoRepository);
        this.evaluateInfoRepository = evaluateInfoRepository;
    }

    public List<EvaluateInfo> getEvaluateInfos(int page, int size, List<OrderRequest> order) {
        Sort sort = getSortBy(order, new EvaluateInfo());
        List<EvaluateInfo> result = evaluateInfoRepository.findByDeleted(false,sort);
        return result;
    }
}
