package ir.shop.shop.service;

import ir.shop.shop.dto.requests.LoginRequest;
import ir.shop.shop.dto.requests.RegisterRequest;
import ir.shop.shop.exception.EmailExistException;
import ir.shop.shop.exception.UserNotFoundException;
import ir.shop.shop.jwt.JwtService;
import ir.shop.shop.model.Role;
import ir.shop.shop.model.User;
import ir.shop.shop.repository.RoleRepo;
import ir.shop.shop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailExistException();
        }

        Role role = roleRepo.findByName("ROLE_USER").orElseThrow(()
                -> new RuntimeException("Role not found"));

        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(request.getPassword())
                .enabled(false)
                .role(role)
                .build();

        userRepo.save(user);

        return "successfully";
    }

    public String login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            throw new RuntimeException("Password is incorrect");
        }
        if (!user.isEnabled()) {
            throw new RuntimeException("Account is not verified");
        }
        return jwtService.tokenGenerate(user);
    }
}
