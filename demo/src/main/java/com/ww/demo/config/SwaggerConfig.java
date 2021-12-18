package com.ww.demo.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

  public static final String API_KEY_SCHEME = "apiKey";


    @Autowired
    ApplicationProperties properties;

	@Value(value = "${api.key:aaface6d-f3a6-497c-8e16-8a5367cae05f}")
	private String apiKey;

	private static final String API_KEY_STR = "api_key";

	@Bean
	public GroupedOpenApi alertsOpenApi() {
		String[] paths = { "/api/**" };
		String[] packagedToMatch = { "com.ww.demo.controller" };
		return GroupedOpenApi.builder().setGroup("Product Service").pathsToMatch(paths)
				.packagesToScan(packagedToMatch).build();
	}

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("apiKeyScheme",
						new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER)
								.name(API_KEY_STR)))
				.info(new Info().title(properties.getTitle()).description(properties.getDescription()))
				.addSecurityItem(new SecurityRequirement().addList("apiKeyScheme"));
	}
}
