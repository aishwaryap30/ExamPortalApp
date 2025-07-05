package com.myproject.OnlineExamination.service;


import com.myproject.OnlineExamination.model.User;
import com.myproject.OnlineExamination.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User authenticateUser(String loginInput, String password) {
        Optional<User> userOptional = userRepository.findByUserName(loginInput);

        // Try email if username not found
        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(loginInput);
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }

        return null; // Return null if authentication fails
    }

    public User authenticateUserByUsernameOrEmail(String userNameOrEmail, String password) {
        Optional<User> userOpt = userRepository.findByEmail(userNameOrEmail);
        if (!userOpt.isPresent()) {
            userOpt = userRepository.findByUserName(userNameOrEmail);
        }
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return userOpt.get();
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && !"ADMIN".equalsIgnoreCase(user.getRole())) {
            userRepository.deleteById(id);
        }
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    //------------------------------------------
}
