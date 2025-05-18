package com.scaler.services;

import com.scaler.exceptions.UnAuthorizedAccess;
import com.scaler.exceptions.UserNotFoundException;
import com.scaler.models.*;
import com.scaler.repositories.DailyRevenueRepository;
import com.scaler.repositories.UserRepository;
import com.scaler.utils.DateUtils;

import java.util.*;

public class RevenueServiceImpl implements RevenueService {
    private DailyRevenueRepository revenueRepo;
    private UserRepository userRepo;

    public RevenueServiceImpl(DailyRevenueRepository revenueRepo, UserRepository userRepo) {
        this.revenueRepo = revenueRepo;
        this.userRepo = userRepo;
    }

    public AggregatedRevenue calculateRevenue(long userId, String queryType) throws UnAuthorizedAccess, UserNotFoundException {
        Optional<User> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User Not found");
        }
        User uu = user.get();
        if (uu.getUserType() != UserType.BILLING) {
            throw new UnAuthorizedAccess("User not allowed");
        }
        RevenueQueryType revenueQueryType = RevenueQueryType.valueOf(queryType);
        Pair<Date, Date> dates = DateUtils.getStartAndEndDateByQueryType(revenueQueryType);
        Date startDate = dates.getLeft();
        Date endDate = dates.getRight();
        List<DailyRevenue> dailyRevenueBetweenDates = revenueRepo.getDailyRevenueBetweenDates(startDate, endDate);
        double totalRevenueFromFoodSales = dailyRevenueBetweenDates.stream().mapToDouble(DailyRevenue::getRevenueFromFoodSales).sum();
        double totalGst = dailyRevenueBetweenDates.stream().mapToDouble(DailyRevenue::getTotalGst).sum();
        double totalServiceCharge = dailyRevenueBetweenDates.stream().mapToDouble(DailyRevenue::getTotalServiceCharge).sum();
        AggregatedRevenue aggregatedRevenue = new AggregatedRevenue();
        aggregatedRevenue.setTotalRevenue(totalRevenueFromFoodSales + totalGst + totalServiceCharge);
        aggregatedRevenue.setTotalGst(totalGst);
        aggregatedRevenue.setTotalServiceCharge(totalServiceCharge);
        aggregatedRevenue.setFromDate(startDate);
        aggregatedRevenue.setToDate(endDate);
        aggregatedRevenue.setRevenueFromFoodSales(totalRevenueFromFoodSales);


        return aggregatedRevenue;
    }
}
