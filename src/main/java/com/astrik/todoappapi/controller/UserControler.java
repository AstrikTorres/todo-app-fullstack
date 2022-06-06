package com.astrik.todoappapi.controller;

import com.astrik.todoappapi.entity.User;
import com.astrik.todoappapi.entity.UserCreate;
import com.astrik.todoappapi.entity.UserUpdate;
import com.astrik.todoappapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserControler {

    private UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.reedUsers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(
            @RequestBody @Validated(UserCreate.class) User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> putUser(
            @RequestBody @Validated(UserUpdate.class) User user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<ObjectNode> deleteUser(@PathVariable @Min(1) Long id) {
        String[] msg = {
                "Deleted User",
                "Try again - verify param"
        };

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        return userService.removeUser(id)
                ? ResponseEntity.status(HttpStatus.OK).body(objectNode.put("message", msg[0]))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectNode.put("message", msg[1]));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ObjectNode> handleConstraintViolationException(ConstraintViolationException e) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectNode.put("message", e.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ObjectNode handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode errors = mapper.createObjectNode();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                                errors.put(
                                        error.getField(),
                                        error.getDefaultMessage()
                                ));
        return errors;
    }
}
