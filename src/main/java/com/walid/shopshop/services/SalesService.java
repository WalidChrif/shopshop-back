package com.walid.shopshop.services;

import java.math.BigDecimal;

public interface SalesService {
    Long getAllSalesCount();

    Long getMonthlySales();

    Long getWeeklySales();

    BigDecimal getEarnings();

    BigDecimal getMonthlyEarnings();

    BigDecimal getWeeklyEarnings();
}
