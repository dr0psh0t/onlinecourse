package com.onlinecourse;

import com.onlinecourse.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OnlinecourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinecourseApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {

			/*userService.saveUser(new User("Daryll", "Dagondon", "dagondon@gmail.com", "daryll", "1234"));
			userService.saveUser(new User("David", "Gwapo", "david@gwapo.com", "david", "1234"));
			userService.saveUser(new User("Dagondon", "Pogi", "dagondon@pogi.com", "dagondon", "1234"));*/
			
			
			/*userService.saveRole(new Role("STUDENT"));
			userService.saveRole(new Role("INSTRUCTOR"));
			userService.saveRole(new Role("ADMIN"));
			userService.saveRole(new Role("MODERATOR"));
			
			userService.saveUser(new User("Daryll", "Dagondon", "dagondon@gmail.com", "daryll", "1234"));
			userService.saveUser(new User("David", "Gwapo", "david@gwapo.com", "david", "1234"));
			userService.saveUser(new User("Dagondon", "Pogi", "dagondon@pogi.com", "dagondon", "1234"));

			userService.addRoleToUser("daryll", "STUDENT");
			userService.addRoleToUser("david", "INSTRUCTOR");
			userService.addRoleToUser("dagondon", "STUDENT");

			userService.addCreatedCourseToInstructor("david", "Algebra");
			userService.addTakenCourseToStudent("dagondon", "Algebra");
			userService.addTakenCourseToStudent("daryll", "Algebra");*/
		};
	}
}