package com.ecommerce.project.config;

import com.ecommerce.project.repositories.UserRepository;
import org.springframework.context.annotation.Configuration;
import com.ecommerce.project.entity.User;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class DataInitializer {



    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByEmail("admin@test.com").isEmpty()) {

                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@test.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ROLE_ADMIN");

                userRepository.save(admin);

                System.out.println("Admin user created");
            }
        };
    }
}
