package com.example.diary.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Authentication", bearerAuthentication()))
                .info(apiInfo());
    }

    public SecurityScheme bearerAuthentication() {
        return new SecurityScheme()
                .name("Authorization")
                .description("JWT token")
                .type(SecurityScheme.Type.APIKEY)
                .scheme("bearer")
                .bearerFormat("JWT");
    }

    private Info apiInfo() {
        Contact contact = new Contact();
        contact.setUrl("https://springdoc.org/");
        contact.setName("springdoc 공식문서");
        return new Info()
                .title("Spring Boot Schedule diary")
                .description("Spring Boot Schedule diary")
                .version("1.0.0")
                .contact(contact);
    }
}
