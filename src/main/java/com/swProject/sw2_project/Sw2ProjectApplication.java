package com.swProject.sw2_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class Sw2ProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(Sw2ProjectApplication.class, args);
	}

}
