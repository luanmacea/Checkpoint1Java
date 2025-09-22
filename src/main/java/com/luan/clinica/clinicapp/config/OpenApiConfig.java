package com.luan.clinica.clinicapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI clinicOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ClinicApp API")
                        .description("API da clinica para gerenciamento de pacientes, medicos e consultas.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe ClinicApp")
                                .email("contato@clinicapp.com"))
                        .license(new License()
                                .name("Licenca academica")))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Ambiente local"));
    }
}
