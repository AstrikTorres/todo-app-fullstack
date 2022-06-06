package com.astrik.todoappapi.controller;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.service.ToDoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @PutMapping
    public ResponseEntity<ToDo> putTodo(@RequestBody ToDo toDo) {
        return new ResponseEntity<>(toDoService.updateTodo(toDo), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<ObjectNode> deleteTodo(@PathVariable Long id) {
        String[] msg = {
                "Deleted to do",
                "Try again - verify param"
        };

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();

        return toDoService.removeTodo(id)
                ? ResponseEntity.status(HttpStatus.OK).body(objectNode.put("message", msg[0]))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectNode.put("message", msg[1]));
    }

}
