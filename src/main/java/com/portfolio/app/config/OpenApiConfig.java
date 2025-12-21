package com.portfolio.app.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Mohmed Portfolio API",
                version = "1.0",
                description = "REST APIs for portfolio skills and projects"
        )
)
public class OpenApiConfig {
}
