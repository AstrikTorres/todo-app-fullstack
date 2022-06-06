package com.astrik.todoappapi.controller;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.service.ToDoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    private ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/default")
    public List<ToDo> createDefaultTodos() {
        try {
            return toDoService.setTodosDefault();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping
    public List<ToDo> getAllTodos() {
        return toDoService.reedTodos();
    }

}
