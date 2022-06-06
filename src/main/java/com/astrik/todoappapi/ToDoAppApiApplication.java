package com.astrik.todoappapi;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.service.ToDoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ToDoAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoAppApiApplication.class, args);
    }

}
