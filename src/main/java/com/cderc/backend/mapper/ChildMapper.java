package com.cderc.backend.mapper;
import com.cderc.backend.dto.ChildRequest;
import com.cderc.backend.dto.ChildResponse;
import com.cderc.backend.model.Child;
public class ChildMapper {
    public static Child toEntity(ChildRequest request) {
        Child child = new Child();
        child.setName(request.getName());
        child.setGender(request.getGender());
        child.setHealthStatus(request.getHealthStatus());
        child.setSchoolStatus(request.getSchoolStatus());
        child.setBirthDate(request.getBirthDate());
        return child;
    }

    public static ChildResponse toResponse(Child child) {
        return new ChildResponse(
                child.getId(),
                child.getName(),
                child.getGender(),
                child.getBirthDate(),
                child.getHealthStatus(),
                child.getSchoolStatus(),
                child.getOrganization().getId()
        );
    }
}
