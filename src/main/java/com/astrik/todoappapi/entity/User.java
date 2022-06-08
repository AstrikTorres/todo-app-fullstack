package com.astrik.todoappapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    @NotNull(message = "the id is needed to update", groups = UserUpdate.class)
    private Long id;

    @Column(name = "email", nullable = false, unique = true, length = 50)
    @Email(message = "Email invalid", groups = UserCreate.class)
    private String email;

    @Column(name = "username", nullable = false, length = 50)
    @NotBlank(message = "The username cannot be blank.", groups = UserCreate.class)
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "The password cannot be blank.", groups = UserCreate.class)
    private String password;

    @OneToMany(targetEntity = ToDo.class, mappedBy = "user")
    @JsonIgnore
    private List<ToDo> todos = new ArrayList<>();

    public User() {}

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(List<ToDo> todos) {
        this.todos = todos;
    }
}
