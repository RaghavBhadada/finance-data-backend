package com.raghav.desibusiness.service;

import com.raghav.desibusiness.dto.EmployeeRequestDto;
import com.raghav.desibusiness.dto.RegisterRequestDto;
import com.raghav.desibusiness.entity.*;
import com.raghav.desibusiness.exception.PhoneNotVerifiedException;
import com.raghav.desibusiness.repository.EmailVerificationTokenRepository;
import com.raghav.desibusiness.repository.OtpRepository;
import com.raghav.desibusiness.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.raghav.desibusiness.dto.LoginRequestDto;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.raghav.desibusiness.security.JwtUtil;



@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailVerificationTokenRepository tokenRepository;
    private final OtpRepository otpRepository;




    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       EmailVerificationTokenRepository tokenRepository,
                       OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.otpRepository = otpRepository;
    }

    public String registerOwner(RegisterRequestDto request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return "Phone already exists";
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.OWNER)
                .emailVerified(true)
                .phoneVerified(false)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);

        return "Owner registered successfully";
    }


    public String login(LoginRequestDto request) {

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found";
        }


        User user = optionalUser.get();

        if (user.getStatus() == Status.INACTIVE) {
            return "User is inactive";
        }

        if (!user.isEmailVerified()) {
            return "Please verify your email before logging in";
        }

        // error handling added for only !user.isPhoneVerified() with exception PhoneNotVerifiedException with our exception handler GlobalExceptionHandler
        if (!user.isPhoneVerified()) {
            throw new PhoneNotVerifiedException(
                    "Please verify your phone number before logging in"
            );
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password";
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return token;
    }




    public String generateOtp(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        otpRepository.findByUserId(user.getId())
                .ifPresent(otpRepository::delete);

        String otpValue = String.valueOf(
                (int)(Math.random() * 900000) + 100000
        );

        Otp otp = Otp.builder()
                .otp(otpValue)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(otp);

        System.out.println("OTP for phone verification: " + otpValue);

        return "OTP generated successfully";
    }





    public String verifyOtp(String email, String otpValue) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Otp otp = otpRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "OTP expired";
        }

        if (!otp.getOtp().equals(otpValue)) {
            return "Invalid OTP";
        }

        user.setPhoneVerified(true);
        userRepository.save(user);

        otpRepository.delete(otp);

        return "Phone verified successfully";
    }







    public String createEmployee(EmployeeRequestDto request , Role role) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already exists";
        }

        if (userRepository.existsByPhone(request.getPhone())) {
            return "Phone already exists";
        }

        User employee = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .emailVerified(false)
                .phoneVerified(false)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(employee);

        String token = UUID.randomUUID().toString();

        EmailVerificationToken verificationToken =
                EmailVerificationToken.builder()
                        .token(token)
                        .user(employee)
                        .expiryDate(LocalDateTime.now().plusHours(24))
                        .build();

        tokenRepository.save(verificationToken);

        System.out.println("Verification Link:");
        System.out.println("http://localhost:1111/auth/verify-email?token=" + token);





        return "User created successfully";
    }


    public String updateUserStatus(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if(user.getStatus() == Status.ACTIVE){
            user.setStatus(Status.INACTIVE);
        }
        else{
            user.setStatus(Status.ACTIVE);
        }
        userRepository.save(user);
        return "Status updated successfully";
    }


}
