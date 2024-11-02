package com.mega.e_commerce_system.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Manish Nilkanth",
                        email = "nilkanthmanish0@gmail.com",
                        url = "https://nilkanthportfolio.netlify.app/"
                ),
                description = "OpenApi documentation for E-commerce application APIs for Spring Boot Application",
                title = "E-commerce Application : Back-end API's and configurations",
                version = "1.0",
                license = @License(
                        name = "Apache",
                        url = "https://apache.com"
                ),
                termsOfService = "Free to use"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "This API uses JWT (JSON Web Token) for authentication. To access secured endpoints, users must include a valid JWT in the Authorization header using the format Bearer <token>.",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfiguration {

}
