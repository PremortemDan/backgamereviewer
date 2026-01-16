package com.pagina.pagina.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuración global de CORS 
// Permite que cualquier frontend pueda consumir la API REST pero con cariño

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica a todos los endpoints que empiecen con /api/
                .allowedOriginPatterns("*") // Permite cualquier origen
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("*") // Permite todos los headers
                .allowCredentials(true) // Permite cookies y autenticación
                .maxAge(3600); // Cache de preflight por 1 hora

        // También permitir acceso a Swagger UI desde cualquier origen
        registry.addMapping("/swagger-ui/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);

        registry.addMapping("/v3/api-docs/**") // Permite acceso a Swagger
                .allowedOriginPatterns("*") // Permite cualquier origen para Swagger
                .allowedMethods("GET") // Permite solo GET
                .allowedHeaders("*") // Permite todos los headers
                .allowCredentials(true); // Permite cookies y autenticación
    }
}
