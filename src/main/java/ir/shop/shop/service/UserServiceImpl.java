package ir.shop.shop.service;

import ir.shop.shop.dto.requests.LoginRequest;
import ir.shop.shop.dto.requests.RegisterRequest;
import ir.shop.shop.dto.response.UserResponse;
import ir.shop.shop.repository.RoleRepo;
import ir.shop.shop.repository.UserRepo;
import ir.shop.shop.repository.VerificationRepo;

import java.util.List;

public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final VerificationRepo verificationRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, VerificationRepo verificationRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.verificationRepo = verificationRepo;
    }

    @Override
    public UserResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public String verifyCode(String email, String code) {
        return "";
    }

    @Override
    public String login(LoginRequest request) {
        return "";
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponse updateUser(Long id, RegisterRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

}
