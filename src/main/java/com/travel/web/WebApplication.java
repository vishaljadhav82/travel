package com.travel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.travel.web.config.Role;
import com.travel.web.model.User;
import com.travel.web.repository.UserRepository;

@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WebApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(Role.ADMIN);
        user.setPhoneNumber("8261806711");
        user.setUsername("admin");

        userRepository.findByUsername("admin").ifPresentOrElse(
            existingUser -> {},
            () -> {
                userRepository.save(user);
                System.out.println("Admin user created");
            }
        );
    }
}
