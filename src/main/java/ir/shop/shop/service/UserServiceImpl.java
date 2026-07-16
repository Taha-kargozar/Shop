package ir.shop.shop.service;

import ir.shop.shop.dto.requests.UserUpdateRequest;
import ir.shop.shop.dto.response.UserResponse;
import ir.shop.shop.exception.UserNotFoundException;
import ir.shop.shop.model.User;
import ir.shop.shop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;

    @Override
    public UserResponse getUserById(Long id) {

            User user = userRepo.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            return mapToResponse(user);

    }

    @Override
    public List<UserResponse> getAllUsers() {

            return userRepo.findAll()
                    .stream()
                    .map(this::mapToResponse)
                    .toList();

    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

            User user = userRepo.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            user.setFirstname(request.getFirstname());
            user.setLastname(request.getLastname());



            User updatedUser = userRepo.save(user);



            return mapToResponse(updatedUser);

        }

        @Override
        public void deleteUser(Long id) {

            User user = userRepo.findById(id)
                    .orElseThrow(UserNotFoundException::new);

            userRepo.delete(user);

        }

        private UserResponse mapToResponse(User user){

            return UserResponse.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .email(user.getEmail())
                    .build();

        }

    @Override
    public UserResponse getCurrentUser(String email) {


        User user = userRepo.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);


        return mapToResponse(user);

    }
}
