package com.example.newsapp;

import com.example.newsapp.constant.PRIVILEGES;
import com.example.newsapp.constant.UserRole;
import com.example.newsapp.model.Privilege;
import com.example.newsapp.model.Role;
import com.example.newsapp.model.User;
import com.example.newsapp.repository.PrivilegeRepository;
import com.example.newsapp.repository.RoleRepository;
import com.example.newsapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class NewsappApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsappApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PrivilegeRepository privilegeRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "farhat@mail.kz";
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {

                Set<Privilege> privileges = new HashSet<>();
                List<PRIVILEGES> collect = Arrays.stream(PRIVILEGES.values()).toList();
                collect.forEach(p -> {
                    Privilege privilege = new Privilege();
                    privilege.setName(p.name());
                    privilegeRepository.save(privilege);
                    privileges.add(privilege);
                });

                Role role = new Role();
                role.setName(UserRole.ADMIN.name());
                role.setPrivileges(privileges);
                roleRepository.saveAndFlush(role);

                User u = new User();
                u.setFirstName("Farhat");
                u.setLastName("Amangeldin");
                u.setEmail("farhat@mail.kz");
                u.setPassword(passwordEncoder.encode("123456"));
                u.setUserRole(role);
                u.setEnabled(true);
                userRepository.save(u);
            }
        };
    }

}
