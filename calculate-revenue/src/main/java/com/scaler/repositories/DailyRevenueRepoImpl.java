package com.scaler.repositories;

import com.scaler.models.DailyRevenue;
import com.scaler.utils.DateUtils;

import java.util.*;

public class DailyRevenueRepoImpl implements DailyRevenueRepository {
    Map<Long, DailyRevenue> map = new HashMap<>();
    int id = 0;

    public DailyRevenue save(DailyRevenue dailyRevenue) {
        if (dailyRevenue.getId() == 0) {
            dailyRevenue.setId(++id);
        }
        map.put(dailyRevenue.getId(), dailyRevenue);
        return dailyRevenue;
    }

    public List<DailyRevenue> getDailyRevenueBetweenDates(Date startDate, Date endDate) {
        List<DailyRevenue> list = new ArrayList<>();
        Collection<DailyRevenue> mapList = map.values();
        for (DailyRevenue revenue : mapList) {
            if ((revenue.getDate().compareTo(startDate) >= 0 || DateUtils.equalDates(revenue.getDate(),startDate))
                    && (revenue.getDate().compareTo(endDate) <= 0 || DateUtils.equalDates(revenue.getDate(),endDate))) {
                list.add(revenue);
            }
        }
        return list;
    }
}
