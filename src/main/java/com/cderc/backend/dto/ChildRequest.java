package com.cderc.backend.dto;

import com.cderc.backend.model.SchoolStatus;

import java.time.LocalDate;

public class ChildRequest {
    private String firstName;
    private String lastName;
    private String gender;
    private String healthStatus;
    private LocalDate birthDate;

    private SchoolStatus schoolStatus;
    private String schoolClass;
    private String vocationalTrainingType;

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getGender() {
        return gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public SchoolStatus getSchoolStatus() {
        return schoolStatus;
    }

    public String getSchoolClass() {
        return schoolClass;
    }

    public String getVocationalTrainingType() {
        return vocationalTrainingType;
    }
}
