package com.astrik.todoappapi;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.entity.User;
import com.astrik.todoappapi.repository.ToDoRepository;
import com.astrik.todoappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ToDoAppApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
       setDatabaseDefault();
    }

    @Autowired
    ToDoRepository toDoRepository;
    @Autowired
    UserRepository userRepository;
    public void setDatabaseDefault() {
        User user1 = new User("user1@domain.com", "user1", "user");
        User user2 = new User("user2@domain.com", "user2", "user");
        User user3 = new User("user3@domain.com", "user3", "user");

        List<User> users = Arrays.asList(user1, user2, user3);

        users.stream()
                .forEach(userRepository::save);

        ToDo toDo1 = new ToDo("Cortar cebolla", false, user1);
        ToDo toDo2 = new ToDo("Llorar con la llorona", true, user2);
        ToDo toDo3 = new ToDo("Hacer el curso de Spring Boot", true, user2);
        ToDo toDo4 = new ToDo("Hacer el curso de React", true, user2);
        ToDo toDo5 = new ToDo("to do...", true, user1);
        ToDo toDo6 = new ToDo("to do...", true, user3);

        List<ToDo> toDos = Arrays.asList(toDo1, toDo2, toDo3, toDo4, toDo5, toDo6);

        toDos.stream()
                .forEach(toDoRepository::save);
    }

}
