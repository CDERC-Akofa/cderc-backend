package com.cderc.backend.dto;

import java.math.BigDecimal;

public class OrganizationTotalReportResponse {
    private BigDecimal totalExpenses;

    public OrganizationTotalReportResponse(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }
}
