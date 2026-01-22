package com.pagina.pagina.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//Configuración global de Swagger para toda la API
//Esta clase centraliza toda la documentación y configuración de la API de la pagina

@Configuration
public class SwaggerConfig {

        @Value("${server.port:9090}")
        private String serverPort;

        @Value("${app.backend.url:http://localhost:9090}")
        private String backendUrl;

        // Configuración principal de Swagger para toda la aplicación
        // Define metadatos, servidores, seguridad y documentación externa que se deve
        // crear

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                // Información general de la API
                                .info(new Info()
                                                .title("API de Gestión - Game Reviewer")
                                                .version("1.0.0")
                                                .description("""
                                                                API REST completa para la gestión del sistema Game Reviewer.

                                                                Características principales:
                                                                - Gestión de usuarios y reviews
                                                                - Gestión de juegos
                                                                - Operaciones CRUD completas
                                                                - Respuestas estandarizadas

                                                                Todos los endpoints retornan JSON y siguen las convenciones REST.
                                                                """)
                                                .contact(new Contact()
                                                                .name("Equipo de Desarrollo")
                                                                .email("contacto@gamereviewer.com")
                                                                .url("https://backgamereviewer.onrender.com"))
                                                .license(new License()
                                                                .name("Apache 2.0")
                                                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))

                                // Configuración de servidores
                                .servers(List.of(
                                                new Server()
                                                                .url(backendUrl)
                                                                .description("Servidor Actual"),
                                                new Server()
                                                                .url("http://localhost:" + serverPort)
                                                                .description("Servidor de Desarrollo Local")))

                                // Configuración de seguridad por si las dudas se agrega JWT
                                .components(new Components()
                                                .addSecuritySchemes("bearer-jwt", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")
                                                                .description("Autenticación mediante JWT Token. Formato: 'Bearer {token}'"))
                                                .addSecuritySchemes("api-key", new SecurityScheme()
                                                                .type(SecurityScheme.Type.APIKEY)
                                                                .in(SecurityScheme.In.HEADER)
                                                                .name("X-API-Key")
                                                                .description("API Key para autenticación alternativa")))
                                // Documentación externa
                                .externalDocs(new ExternalDocumentation()
                                                .description("Documentación completa del proyecto")
                                                .url("https://docs.pagina.com"));

                // Aplicar seguridad globalmente, descomentar la linea de abajo
                // .addSecurityItem(new SecurityRequirement().addList("bearer-jwt"));
        }
}
