package com.cderc.backend.dto;

import java.math.BigDecimal;

public class YearReportResponse {
    private int year;
    private BigDecimal total;

    public YearReportResponse(int year, BigDecimal total) {
        this.year = year;
        this.total = total;
    }

    public int getYear() { return year; }
    public BigDecimal getTotal() { return total; }
}
