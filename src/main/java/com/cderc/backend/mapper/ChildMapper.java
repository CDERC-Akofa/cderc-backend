package com.cderc.backend.mapper;
import com.cderc.backend.dto.ChildRequest;
import com.cderc.backend.dto.ChildResponse;
import com.cderc.backend.model.Child;
import java.time.LocalDate;
import java.time.Period;
public class ChildMapper {
    public static Child toEntity(ChildRequest request) {
        Child child = new Child();
        child.setFirstName(request.getFirstName());
        child.setLastName(request.getLastName());
        child.setGender(request.getGender());
        child.setHealthStatus(request.getHealthStatus());
        child.setSchoolStatus(request.getSchoolStatus());
        child.setBirthDate(request.getBirthDate());
        child.setSchoolClass(request.getSchoolClass());
        child.setVocationalTrainingType(request.getVocationalTrainingType());
        return child;
    }

    public static ChildResponse toResponse(Child child) {
        Integer age = null;

        if (child.getBirthDate() != null) {
            age = Period.between(
                    child.getBirthDate(),
                    LocalDate.now()
            ).getYears();
        }

        return new ChildResponse(
                child.getId(),
                child.getFirstName(),
                child.getLastName(),
                child.getGender(),
                child.getBirthDate(),
                age,
                child.getHealthStatus(),
                child.getOrganization().getId(),
                child.getSchoolStatus(),
                child.getSchoolClass(),
                child.getVocationalTrainingType()
        );
    }
}
