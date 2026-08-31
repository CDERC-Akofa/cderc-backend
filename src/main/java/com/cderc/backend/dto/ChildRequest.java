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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setSchoolStatus(SchoolStatus schoolStatus) {
        this.schoolStatus = schoolStatus;
    }

    public void setSchoolClass(String schoolClass) {
        this.schoolClass = schoolClass;
    }

    public void setVocationalTrainingType(String vocationalTrainingType) {
        this.vocationalTrainingType = vocationalTrainingType;
    }

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
