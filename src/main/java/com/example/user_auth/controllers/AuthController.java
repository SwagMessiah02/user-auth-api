package com.example.user_auth.controllers;

import com.example.user_auth.EmailValidator;
import com.example.user_auth.dtos.AuthResponseDTO;
import com.example.user_auth.dtos.LoginDTO;
import com.example.user_auth.dtos.RegisterDTO;
import com.example.user_auth.exceptions.InvalidEmailException;
import com.example.user_auth.exceptions.InvalidRequestException;
import com.example.user_auth.exceptions.UserAlreadyExistsException;
import com.example.user_auth.models.User;
import com.example.user_auth.repository.RoleRepository;
import com.example.user_auth.repository.UserRepository;
import com.example.user_auth.security.JwtTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailValidator emailValidator;

    public AuthController(JwtTokenService jwtTokenService,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          EmailValidator emailValidator) {

        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.emailValidator = emailValidator;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginData, HttpServletResponse response)
            throws AuthenticationException, InvalidEmailException {
        if(!emailValidator.INSTANCE.matcher(loginData.email().trim()).matches()) {
            throw new InvalidEmailException("Email inválido");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.email(), loginData.password()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Assert.notNull(userDetails, "Error ao tentar realizar login.");

        String token = jwtTokenService.generateToken(userDetails);

        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setAttribute("SameSite", "Strict");
        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthResponseDTO(token, "Login realizado com sucesso"));
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterDTO registerData) throws InvalidRequestException {
        User user = new User();

        if(!emailValidator.INSTANCE.matcher(registerData.email().trim()).matches()) {
            throw new InvalidRequestException("Invalid email");
        }

        if(userRepository.findUserByEmail(registerData.email().trim()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }

        user.setName(registerData.name().trim());
        user.setEmail(registerData.email().trim());
        user.setPassword(passwordEncoder.encode(registerData.password().trim()));
        user.setRoles(Set.of(roleRepository.findById(2L).orElseThrow(() ->
            new RuntimeException("Error: can't find role"))));

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }
}