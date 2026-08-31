package com.cderc.backend.dto;

import com.cderc.backend.model.SchoolStatus;

import java.time.LocalDate;

public class
ChildResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private String healthStatus;
    private Long organizationId;
    private LocalDate birthDate;
    private Integer age;
    private final SchoolStatus schoolStatus;
    private final String schoolClass;
    private final String vocationalTrainingType;

    public ChildResponse(Long id, String firstName, String lastName, String gender,LocalDate birthDate,
                         Integer age, String healthStatus,
                         Long organizationId, SchoolStatus schoolStatus,
                         String schoolClass,
                         String vocationalTrainingType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.age = age;
        this.healthStatus = healthStatus;
        this.organizationId = organizationId;
        this.schoolStatus = schoolStatus;
        this.schoolClass = schoolClass;
        this.vocationalTrainingType = vocationalTrainingType;

    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public Integer getAge(){
        return age;
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

    public SchoolStatus getSchoolStatus() {
        return schoolStatus;
    }

    public String getSchoolClass(){
        return schoolClass;
    }
    public String getVocationalTrainingType() {
        return vocationalTrainingType;
    }

    public Long getOrganizationId() {
        return organizationId;
    }
}
