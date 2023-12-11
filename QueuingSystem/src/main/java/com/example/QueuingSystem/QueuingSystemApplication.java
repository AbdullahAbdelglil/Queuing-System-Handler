package com.example.QueuingSystem;

import com.example.QueuingSystem.model.SystemInputs;
import com.example.QueuingSystem.services.SystemServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QueuingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(QueuingSystemApplication.class, args);
	}

}
