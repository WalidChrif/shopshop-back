package com.walid.shopshop.services;

import com.walid.shopshop.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SalesServiceImpl implements SalesService{

    @Autowired
    OrderRepo orderRepo;

    @Override
    public Long getAllSalesCount() {
        return orderRepo.count();
    }

    @Override
    public Long getMonthlySales() {
        return orderRepo.count()/12;
    }

    @Override
    public Long getWeeklySales() {
        return orderRepo.count()/52;
    }

    @Override
    public BigDecimal getEarnings() {
        return orderRepo.findTotalEarnings();
    }

    @Override
    public BigDecimal getMonthlyEarnings() {
        return orderRepo.findTotalEarnings().divide(BigDecimal.valueOf(12), RoundingMode.CEILING);
    }

    @Override
    public BigDecimal getWeeklyEarnings() {
        return orderRepo.findTotalEarnings().divide(BigDecimal.valueOf(52), RoundingMode.CEILING);
    }
}
