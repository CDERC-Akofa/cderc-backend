package com.cderc.backend.dto;

import java.time.LocalDate;

public class
ChildResponse {

    private Long id;
    private String name;
    private String gender;
    private String healthStatus;
    private String schoolStatus;
    private Long organizationId;
    private LocalDate birthDate;

    public ChildResponse(Long id, String name, String gender,LocalDate birthDate,
                         String healthStatus, String schoolStatus,
                         Long organizationId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthDate = birthDate;
        this.healthStatus = healthStatus;
        this.schoolStatus = schoolStatus;
        this.organizationId = organizationId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public String getSchoolStatus() {
        return schoolStatus;
    }

    public Long getOrganizationId() {
        return organizationId;
    }
}
