package com.albin.productcatalog;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Product Catalog Management System.
 * <p>
 * This application manages products, producers, and product attributes
 * through a RESTful API backed by a PostgreSQL database.
 * </p>
 * <p>
 * Environment variables (DB_NAME, DB_USERNAME, DB_PASSWORD) are loaded from
 * a {@code .env} file at startup using the dotenv library, so that sensitive
 * credentials are kept out of version-controlled configuration files.
 * </p>
 */
@SpringBootApplication
public class ProductCatalogApplication {

	public static void main(String[] args) {

		// Load environment variables from the .env file in the project root
		Dotenv dotenv = Dotenv.load();

		// Propagate database credentials into JVM system properties so that
		// Spring's property placeholders (${DB_NAME}, etc.) in application.yaml
		// can resolve them at runtime.
		System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

		// Bootstrap the Spring application context
		SpringApplication.run(ProductCatalogApplication.class, args);
	}

}
