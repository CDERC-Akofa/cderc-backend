package com.cderc.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CDERC Backend API",
                version = "1.0",
                description = """
                        REST API für das CDERC-Verwaltungssystem.

                        Funktionen:
                        • Benutzerverwaltung
                        • Organisationen
                        • Mitglieder
                        • Kinderverwaltung
                        • Veranstaltungen
                        • Ausgabenverwaltung
                        • Reinigungspläne
                        • Berichte

                        Authentifizierung erfolgt über JWT Bearer Token.
                        """
        )
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
