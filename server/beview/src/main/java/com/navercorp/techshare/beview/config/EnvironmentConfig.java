package com.navercorp.techshare.beview.config;

/**
 * Created by Naver on 2017. 1. 19..
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class EnvironmentConfig {
	@Configuration
	@PropertySource(
		ignoreResourceNotFound = true,
		value = {
			"classpath:/properties/database.properties",
			"file:/home1/irteam/deploy/beview/dist/project_properties/database.properties"
		}
	)

	public static class ProductionProperties {
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
			return new PropertySourcesPlaceholderConfigurer();
		}
	}
}