package com.cderc.backend.dto;

public class CreateAdminRequest {
    private String name;
    private String email;
    private String password;
    private Long organizationId;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getOrganizationId() {
        return organizationId;
    }
}
