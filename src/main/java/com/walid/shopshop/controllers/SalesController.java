package com.walid.shopshop.controllers;

import com.walid.shopshop.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@CrossOrigin(origins = {"https://localhost:4200", "http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {

    @Autowired
    SalesService salesService;

    @GetMapping()
    public Long getAllSales(){
        return salesService.getAllSalesCount();
    }
    @GetMapping("/monthly-sales")
    public Long getMonthlySales(){
        return salesService.getMonthlySales();
    }
    @GetMapping("/weekly-sales")
    public Long getWeeklySales(){
        return salesService.getWeeklySales();
    }
    @GetMapping("/earnings")
    public BigDecimal getEarnings() {
        return salesService.getEarnings();
    }
    @GetMapping("/monthly-earnings")
    public BigDecimal getMonthlyEarnings() {
        return salesService.getMonthlyEarnings();
    }
    @GetMapping("/weekly-earnings")
    public BigDecimal getWeeklyEarnings() {
        return salesService.getWeeklyEarnings();
    }

}
