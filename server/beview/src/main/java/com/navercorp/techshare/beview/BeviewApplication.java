package com.navercorp.techshare.beview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeviewApplication.class, args);
		System.out.println("server on");
	}
}
