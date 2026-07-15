package ir.shop.shop.controller;

import ir.shop.shop.dto.requests.UserUpdateRequest;
import ir.shop.shop.dto.response.UserResponse;
import ir.shop.shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){

        return ResponseEntity.ok(
                userService.getUserById(id)
        );

    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){

        return ResponseEntity.ok(
                userService.getAllUsers()
        );

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request){

        return ResponseEntity.ok(
                userService.updateUser(id, request)
        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){

        userService.deleteUser(id);

        return ResponseEntity.ok("User deleted successfully");

    }

}
