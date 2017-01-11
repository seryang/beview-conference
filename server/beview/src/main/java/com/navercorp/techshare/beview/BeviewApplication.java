package com.navercorp.techshare.beview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class BeviewApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BeviewApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(BeviewApplication.class, args);
		System.out.println("server on");
	}
}
