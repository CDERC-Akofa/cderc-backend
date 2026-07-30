package com.cderc.backend.dto;

public class OrganizationResponse {

    private Long id;
    private String name;
    private String email;
    private String themeColor;
    private String logo;

    public OrganizationResponse(Long id,
                                String name,
                                String email,
                                String themeColor,
                                String logo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.themeColor = themeColor;
        this.logo = logo;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getThemeColor() { return themeColor; }
    public String getLogo() { return logo; }
}
