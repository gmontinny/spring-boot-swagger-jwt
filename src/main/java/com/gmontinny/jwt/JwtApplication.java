package com.gmontinny.jwt;

import com.gmontinny.jwt.model.AppUser;
import com.gmontinny.jwt.model.AppUserRole;
import com.gmontinny.jwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtApplication implements CommandLineRunner {

	final UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		AppUser admin = new AppUser();
		admin.setUsername("admin");
		admin.setPassword("admin");
		admin.setEmail("admin@email.com");
		admin.setAppUserRoles(new ArrayList<AppUserRole>(Collections.singletonList(AppUserRole.ROLE_ADMIN)));

		userService.signup(admin);

		AppUser client = new AppUser();
		client.setUsername("client");
		client.setPassword("client");
		client.setEmail("client@email.com");
		client.setAppUserRoles(new ArrayList<AppUserRole>(Collections.singletonList(AppUserRole.ROLE_CLIENT)));

		userService.signup(client);
	}
}
