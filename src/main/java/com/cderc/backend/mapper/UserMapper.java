package com.cderc.backend.mapper;

import com.cderc.backend.dto.UserResponse;
import com.cderc.backend.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getOrganization() != null ? user.getOrganization().getId() : null
        );
    }
}
