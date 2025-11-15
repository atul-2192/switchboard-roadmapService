package com.Roadmap_Service.Roadmap.Service.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        log.info("SwaggerConfig :: customOpenAPI() :: Initializing :: OpenAPI Configuration");
        
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8082");
        localServer.setDescription("Local Development Server");

        Contact contact = new Contact();
        contact.setName("Roadmap Service Team");
        contact.setEmail("support@roadmapservice.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Roadmap Service API")
                .version("1.0.0")
                .description("API documentation for Roadmap Service - Manage roadmap assignments and tasks")
                .contact(contact)
                .license(license);

        OpenAPI openAPI = new OpenAPI()
                .info(info)
                .servers(List.of(localServer));

        log.info("SwaggerConfig :: customOpenAPI() :: Configured Successfully :: OpenAPI");
        return openAPI;
    }
}
