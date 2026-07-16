package ir.shop.shop.service;

import ir.shop.shop.dto.requests.UserUpdateRequest;
import ir.shop.shop.dto.response.UserResponse;

import java.util.List;

public interface UserService {

        UserResponse getUserById(Long id);

        List<UserResponse> getAllUsers();

        UserResponse updateUser(Long id, UserUpdateRequest request);

        void deleteUser(Long id);

        UserResponse getCurrentUser(String email);
}
