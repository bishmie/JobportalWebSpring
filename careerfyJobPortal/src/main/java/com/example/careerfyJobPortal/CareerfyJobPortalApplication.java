package com.example.careerfyJobPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class CareerfyJobPortalApplication {

	public static void main(String[] args) {
		File uploadDir = new File("./uploads");
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		SpringApplication.run(CareerfyJobPortalApplication.class, args);
	}

}
