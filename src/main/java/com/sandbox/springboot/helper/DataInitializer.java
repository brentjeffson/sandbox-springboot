package com.sandbox.springboot.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.sql.init.dependency.DependsOnDatabaseInitialization;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sandbox.springboot.model.User;
import com.sandbox.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;

@Component
@DependsOnDatabaseInitialization
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${initial-users.admin.username}")
    private String adminUsername;

    @Value("${initial-users.admin.password}")
    private String adminPassword;

    @Value("${initial-users.user.username}")
    private String userUsername;

    @Value("${initial-users.user.password}")
    private String userPassword;
    
    @Override
    public void run(String... args) {
        String[][] users = {
            {adminUsername, adminPassword, "ROLE_ADMIN"},
            {userUsername, userPassword, "ROLE_USER"}
        };

        for (String[] userData : users) {
            String username = userData[0];
            String password = userData[1];
            String role = userData[2];

            if (userRepository.findByUsername(username).isEmpty()) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(password));
                user.setRole(role);
                userRepository.save(user);
                logger.info("Created user: {} with role: {}", username, role);
            } else {
                logger.info("User: {} already exists", username);
            }
        }
    }
}