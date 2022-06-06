package com.astrik.todoappapi.service;

import com.astrik.todoappapi.entity.User;
import com.astrik.todoappapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> reedUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.findById(user.getId())
                .map(u -> userRepository.save(user))
                .orElseThrow(() -> new RuntimeException("Id not found"));
    }

    public boolean removeUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else return false;
    }
}
