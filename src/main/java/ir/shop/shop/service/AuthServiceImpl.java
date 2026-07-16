package ir.shop.shop.service;

import ir.shop.shop.dto.requests.LoginRequest;
import ir.shop.shop.dto.requests.RegisterRequest;
import ir.shop.shop.exception.EmailExistException;
import ir.shop.shop.exception.RoleNotFoundException;
import ir.shop.shop.exception.UserNotFoundException;
import ir.shop.shop.exception.VerificationExpiredException;
import ir.shop.shop.jwt.JwtService;
import ir.shop.shop.model.Role;
import ir.shop.shop.model.User;
import ir.shop.shop.model.VerificationCode;
import ir.shop.shop.repository.RoleRepo;
import ir.shop.shop.repository.UserRepo;
import ir.shop.shop.repository.VerificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UserRepo userRepo;
        private final RoleRepo roleRepo;
        private final VerificationRepo verificationRepo;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;

    @Override
    public String register(RegisterRequest request) {

        if(userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new EmailExistException();
        }

        Role role = roleRepo.findByName("ROLE_USER")
                .orElseThrow(RoleNotFoundException::new);

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(false)
                .role(role)
                .build();

        userRepo.save(user);

        return "User registered successfully";
    }

        @Override
        public String verifyCode(String email, String code) {

            User user = userRepo.findByEmail(email)
                    .orElseThrow(UserNotFoundException::new);

            VerificationCode verification =
                    verificationRepo.findByUser(user)
                            .orElseThrow(VerificationExpiredException::new);

            if(!verification.getCode().equals(code)){

                throw new VerificationExpiredException();

            }

            user.setEnabled(true);

            userRepo.save(user);

            verificationRepo.delete(verification);

            return "Email verified successfully";

        }

        @Override
        public String login(LoginRequest request) {

            User user = userRepo.findByEmail(request.getEmail())
                    .orElseThrow(UserNotFoundException::new);

            if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){

                throw new RuntimeException("Invalid password");

            }

            if(!user.isEnabled()){

                throw new RuntimeException("Email is not verified");

            }

            return jwtService.tokenGenerate(user);

        }

}

