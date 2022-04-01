package com.psychoServer.scheduleTask;

import com.psychoServer.entity.ScheduleInfo;
import com.psychoServer.service.ScheduleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Configuration
@EnableScheduling
public class scheduleCreatTask {

    @Autowired
    ScheduleInfoService scheduleInfoService;

    @Scheduled(cron = "0 1 0 1 * ?")
    private void configureTasks() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat week = new SimpleDateFormat("EEE", Locale.ENGLISH);
        int dayOfMonth = getCurrentMonthDay();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date date = calendar.getTime();

        List<ScheduleInfo> scheduleInfos = new ArrayList<>();

        for (Integer i = 0; i < dayOfMonth; i++) {
            ScheduleInfo scheduleInfo = new ScheduleInfo();
            //System.out.println(format.format(date));
            scheduleInfo.setDate(date);
            scheduleInfo.setDayOfWeek(week.format(date).toUpperCase());
            scheduleInfos.add(scheduleInfo);
            calendar.add(calendar.DATE, 1);
            date = calendar.getTime();
        }
        scheduleInfoService.saveOrUpdateAll(scheduleInfos);
    }

    public static int getCurrentMonthDay() {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
