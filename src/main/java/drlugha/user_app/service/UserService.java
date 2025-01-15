package drlugha.user_app.service;

import drlugha.user_app.dto.UserDTO;
import drlugha.user_app.entity.User;

// UserService.java (Service)
public interface UserService {
    void registerUser(UserDTO userDTO);
    User loginUser(UserDTO userDTO); // Change the return type to User
    User getUserByEmail(String email); // New method to retrieve user by email
}
