package com.cderc.backend.dto;

import java.time.LocalDate;

public class GenerateCleaningScheduleRequest {
    private LocalDate startDate;
    private int numberOfWeeks;

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getNumberOfWeeks() {
        return numberOfWeeks;
    }
}
