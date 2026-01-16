package com.pagina.pagina;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class PaginaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaginaApplication.class, args);
	}

	@Bean
	public CommandLineRunner showStartupInfo(Environment env) {
		return args -> {
			String port = env.getProperty("server.port", "8080");

			System.out.println("\n" + "=".repeat(60));
			System.out.println("✅  Aplicación corriendo en puerto: " + port);
			System.out.println("📄  Swagger UI: http://localhost:" + port + "/swagger-ui.html");
			System.out.println("🌐  API REST:   http://localhost:" + port + "/api");
			System.out.println("=".repeat(60) + "\n");
		};
	}
}
