package com.raghav.desibusiness.controller;

import com.raghav.desibusiness.dto.EmployeeRequestDto;
import com.raghav.desibusiness.dto.RegisterRequestDto;
import com.raghav.desibusiness.entity.EmailVerificationToken;
import com.raghav.desibusiness.entity.Role;
import com.raghav.desibusiness.entity.User;
import com.raghav.desibusiness.repository.EmailVerificationTokenRepository;
import com.raghav.desibusiness.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.raghav.desibusiness.dto.LoginRequestDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final EmailVerificationTokenRepository tokenRepository;


    public AuthController(UserService userService,
                          EmailVerificationTokenRepository tokenRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/register-owner")
    public String registerOwner(@Valid @RequestBody RegisterRequestDto request) {
        return userService.registerOwner(request);
    }




    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequestDto request) {
        return userService.login(request);
    }
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam String token) {

        EmailVerificationToken verificationToken =
                tokenRepository.findByToken(token)
                        .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = verificationToken.getUser();
        user.setEmailVerified(true);

        tokenRepository.delete(verificationToken);

        return "Email verified successfully";
    }



    @PostMapping("/generate-otp")
    public String generateOtp(@RequestParam String email) {
        return userService.generateOtp(email);
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String email,
                            @RequestParam String otp) {
        return userService.verifyOtp(email, otp);
    }



}

