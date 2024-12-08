package com.uber.uber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UberApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(UberApplication.class);
		// application.setBannerMode(Banner.Mode.OFF);
		application.run(args);
	}

}
