package com.astrik.todoappapi.controller;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return toDoService.setTodosDefault();
    }

    @GetMapping
    public List<ToDo> getAllTodos() {
        return toDoService.reedTodos();
    }

    @PostMapping
    public ResponseEntity<ToDo> createTodo(@RequestBody ToDo toDo) {
        return new ResponseEntity<>(toDoService.saveTodo(toDo), HttpStatus.CREATED);
    }

}
