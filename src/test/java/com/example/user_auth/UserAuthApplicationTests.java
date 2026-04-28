package com.example.user_auth;

import com.example.user_auth.controllers.AuthController;
import com.example.user_auth.dtos.LoginDTO;
import com.example.user_auth.dtos.RegisterDTO;
import com.example.user_auth.exceptions.InvalidRequestException;
import com.example.user_auth.repository.RoleRepository;
import com.example.user_auth.repository.UserRepository;
import com.example.user_auth.security.JwtTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserAuthApplicationTests {

}
