package com.astrik.todoappapi.service;

import com.astrik.todoappapi.entity.User;
import com.astrik.todoappapi.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ToDoService toDoService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       ToDoService toDoService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.toDoService = toDoService;
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
        User userCreated = userRepository.save(user);
        toDoService.setTodosDefault(userCreated.getId());
        return userCreated;
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
