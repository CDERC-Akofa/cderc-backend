package com.cderc.backend.mapper;

import com.cderc.backend.dto.OrganizationResponse;
import com.cderc.backend.model.Organization;

public class OrganizationMapper {
    public static OrganizationResponse toResponse(Organization organization) {

        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getEmail(),
                organization.getThemeColor(),
                organization.getLogo()
        );
    }
}
