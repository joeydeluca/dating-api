package com.joe.dating;

import com.joe.dating.domain.location.*;
import com.joe.dating.domain.user.User;
import com.joe.dating.domain.user.UserRepository;
import com.joe.dating.domain.user.models.CompletionStatus;
import com.joe.dating.domain.user.models.Profile;
import com.joe.dating.domain.user.models.Smoke;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatingApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {

		};
	}
}
