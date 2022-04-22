package com.psychoServer.scheduleTask;

import com.psychoServer.entity.CounselorInfo;
import com.psychoServer.service.CounselorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Configuration
@EnableScheduling
public class counselTodayTask {

    @Autowired
    private CounselorInfoService counselorInfoService;

    @Scheduled(cron = "0 1 0 * * ?")
    private void configureTasks() {
        List<CounselorInfo> counselorInfos = counselorInfoService.getList();
        for (CounselorInfo counselorInfo : counselorInfos) {
            counselorInfo.setCounselToday(0);
            counselorInfo.setCounselTime(0L);
        }
        counselorInfoService.saveOrUpdateAll(counselorInfos);
    }
}
