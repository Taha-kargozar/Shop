package ir.shop.shop.service;

import ir.shop.shop.dto.requests.LoginRequest;
import ir.shop.shop.dto.requests.RegisterRequest;

public interface AuthService {

        String  register(RegisterRequest request);

        String verifyCode(String email, String code);

        String login(LoginRequest request);

}
