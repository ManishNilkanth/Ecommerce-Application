package com.mega.e_commerce_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ECommerceSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommerceSystemApplication.class, args);
	}

}
