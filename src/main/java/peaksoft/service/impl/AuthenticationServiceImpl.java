package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.register.AuthenticationResponse;
import peaksoft.dto.register.reguest.SignInRequest;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.repository.UserRepository;
import peaksoft.service.AuthenticationService;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostConstruct
    public void init() {
        String email = "admin@gmail.com";
        if (!userRepository.existsByEmail(email)) {
            User user = new User();
            user.setFirstName("Muza");
            user.setLastName("Dian");
            user.setDateOfBirth(LocalDate.of(2002,7,28));
            user.setEmail(email);
            user.setPhoneNumber("+996708860076");
            user.setPassword(passwordEncoder.encode("muza"));
            user.setRole(Role.ADMIN);
            user.setExperience(1);
            userRepository.save(user);
        }
    }



 /*   @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Email already exists");
        }
        User user = User
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfStart())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .experience(request.getExperience())
                .build();

        userRepository.save(user);

        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }*/


    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        User user = userRepository.getUserByEmail(request.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("User with email: " + request.getEmail() + " not found!")
        );

        if (request.getPassword().isBlank()) {
            throw new BadCredentialsException("Password is blank");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong password!");
        }

        return AuthenticationResponse
                .builder()
                .token(jwtService.generateToken(user))
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
