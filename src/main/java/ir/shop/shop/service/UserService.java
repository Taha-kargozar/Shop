package ir.shop.shop.service;

import ir.shop.shop.dto.requests.LoginRequest;
import ir.shop.shop.dto.requests.RegisterRequest;
import ir.shop.shop.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);

    String verifyCode(String email , String code);

    String login(LoginRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id , RegisterRequest request);

    void deleteUser(Long id);

}
