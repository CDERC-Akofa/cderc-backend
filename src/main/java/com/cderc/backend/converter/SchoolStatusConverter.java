package com.cderc.backend.converter;

import com.cderc.backend.model.SchoolStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SchoolStatusConverter implements AttributeConverter<SchoolStatus, String> {
    @Override
    public String convertToDatabaseColumn(SchoolStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public SchoolStatus convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        try {
            return SchoolStatus.valueOf(dbData.trim());
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
}
