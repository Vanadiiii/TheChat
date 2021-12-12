package me.imatveev.thechat.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "The Chat",
                version = "1.0",
                contact = @Contact(
                        name = "Ivan Matveev",
                        url = "https://t.me/vanadiiii42",
                        email = "vanadiiii42@gmail.com"
                )
        )
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "bearerToken",
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SpringdocConfig {
}
