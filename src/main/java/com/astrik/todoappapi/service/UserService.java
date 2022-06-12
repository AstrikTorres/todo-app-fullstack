package com.astrik.todoappapi.service;

import com.astrik.todoappapi.entity.User;
import com.astrik.todoappapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<User> reedUsers() {
        return userRepository.findAll();
    }

    public User getAuthUser() {
        return userRepository.findByUsername(
                (String) (SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }


    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.findById(user.getId())
                .map(u -> {
                    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("Id not found"));
    }

    public boolean removeUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else return false;
    }

}
