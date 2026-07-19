package com.swiftcart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI swiftCartOpenAPI() {
		
		return new OpenAPI()
				.info(new Info()
						.title("SwiftCart E-Commerce REST APIs")
						.version("1.0")
						.description("REST API documentation for SwiftCart.")
						.contact(new Contact()
								.name("Ojesh Bisen")
								.email("ojasbisen624@gmail.com"))
						.license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
	}
}
