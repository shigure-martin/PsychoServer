package com.psychoServer.service;

import com.psychoServer.entity.EvaluateInfo;
import com.psychoServer.repository.EvaluateInfoRepository;
import com.psychoServer.request.OrderRequest;
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
