package com.example.user_auth;

import com.example.user_auth.models.Role;
import com.example.user_auth.models.User;
import com.example.user_auth.repository.RoleRepository;
import com.example.user_auth.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CreateAdminUser implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateAdminUser(UserRepository userRepository, RoleRepository roleRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(userRepository.findUserByEmail("admin@gmail.com").isEmpty()
                && userRepository.findUserByEmail("basic@gmail.com").isEmpty()) {
            User userAdmin = new User();
            Role roleAdmin = roleRepository.findById(1L).orElseThrow(RuntimeException::new);

            userAdmin.setName("Admin");
            userAdmin.setEmail("admin@gmail.com");
            userAdmin.setPassword(passwordEncoder.encode("12345"));
            userAdmin.setRoles(Set.of(roleAdmin));

            User userBasic = new User();
            Role roleBasic = roleRepository.findById(2L).orElseThrow(RuntimeException::new);

            userBasic.setName("Basic");
            userBasic.setEmail("basic@gmail.com");
            userBasic.setPassword(passwordEncoder.encode("12345"));
            userBasic.setRoles(Set.of(roleBasic));

            userRepository.save(userAdmin);
            userRepository.save(userBasic);
        }
    }
}
