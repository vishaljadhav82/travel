package com.travel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.travel.web.config.Role;
import com.travel.web.model.User;
import com.travel.web.repository.UserRepository;

@SpringBootApplication
public class WebApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		User user = new User();
		user.setEmail("admin@gmail.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRole(Role.ADMIN);
		user.setPhoneNumber("8261806711");
		user.setUsername("admin");

		userRepository.findByUsername("admin").ifPresentOrElse(user1 ->{},()->{
			userRepository.save(user);
			System.out.println("user created");
		});
	}

}
