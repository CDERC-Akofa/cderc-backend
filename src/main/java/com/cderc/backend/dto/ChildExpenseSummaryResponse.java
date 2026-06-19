package com.cderc.backend.dto;

import java.math.BigDecimal;

public class ChildExpenseSummaryResponse {
    private Long childId;
    private String childName;

    private  BigDecimal totalExpenses;

    public ChildExpenseSummaryResponse(Long id, String name, BigDecimal total) {
        this.childId = id;
        this.childName = name;
        this.totalExpenses = total;
    }
    public Long getChildId() {
        return childId;
    }

    public String getChildName() {
        return childName;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }
}
