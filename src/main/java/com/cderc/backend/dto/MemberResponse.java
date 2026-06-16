package com.cderc.backend.dto;

import com.cderc.backend.model.MemberStatus;
import com.cderc.backend.model.MemberType;

import java.time.LocalDate;

public class MemberResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private LocalDate birthDate;
    private LocalDate joinedAt;
    private MemberStatus status;
    private MemberType type;
    private Long organizationId;

    public MemberResponse(Long id, String firstName, String lastName, String email,
                          String phone, String address, String city, String country,
                          LocalDate birthDate, LocalDate joinedAt,
                          MemberStatus status, MemberType type, Long organizationId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.country = country;
        this.birthDate = birthDate;
        this.joinedAt = joinedAt;
        this.status = status;
        this.type = type;
        this.organizationId = organizationId;
    }
}
