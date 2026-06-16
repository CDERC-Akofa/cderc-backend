package com.cderc.backend.dto;

import java.time.LocalDate;

public class ChildRequest {
    private String name;
    private String gender;
    private String healthStatus;
    private String schoolStatus;
    private LocalDate birthDate;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public String getSchoolStatus() {
        return schoolStatus;
    }
}
