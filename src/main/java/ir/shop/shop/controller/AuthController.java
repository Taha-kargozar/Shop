package ir.shop.shop.controller;

import ir.shop.shop.dto.requests.LoginRequest;
import ir.shop.shop.dto.requests.RegisterRequest;
import ir.shop.shop.dto.requests.VerifyRequest;
import ir.shop.shop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        String massage = authService.register(request);

        return ResponseEntity.ok(massage);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);

        return ResponseEntity.ok(token);

    }

    @PostMapping("/verify")
    public ResponseEntity<String> verify(@RequestBody VerifyRequest request){
        return ResponseEntity.ok(authService.verifyCode(request.getEmail(), request.getCode()));
    }

}
