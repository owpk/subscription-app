package com.owpk.subscription.config;

import java.util.UUID;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.owpk.subscription.jpa.AppUser;
import com.owpk.subscription.jpa.UserRepository;

@Configuration
public class AppBeanConfig {

    @Bean
    ApplicationRunner createInitialAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            String rawPassword = UUID.randomUUID().toString().substring(0, 8);
            AppUser admin = new AppUser();
            admin.setUserName("admin");
            admin.setPassword(encoder.encode(rawPassword));
            admin.setRole("META_USER");
            userRepository.save(admin);

            System.out.println("🔐 Initial admin user created:");
            System.out.println("   Username: admin");
            System.out.println("   Password: " + rawPassword);
        };
    }
}
