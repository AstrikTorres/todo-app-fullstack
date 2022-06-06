package com.astrik.todoappapi.controller;

import com.astrik.todoappapi.entity.ToDoCreate;
import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.entity.ToDoUpdate;
import com.astrik.todoappapi.service.ToDoService;
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
@RequestMapping("/todos")
@Validated
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
    public ResponseEntity<ToDo> createTodo(@RequestBody @Validated(ToDoCreate.class) ToDo toDo) {
        return new ResponseEntity<>(toDoService.saveTodo(toDo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ToDo> putTodo(@RequestBody @Validated(ToDoUpdate.class) ToDo toDo) {
        return new ResponseEntity<>(toDoService.updateTodo(toDo), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<ObjectNode> deleteTodo(@PathVariable @Min(1) Long id) {
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
                .forEach(
                        error ->
                                errors.put(
                                        error.getField(),
                                        error.getDefaultMessage()
                                ));

        return errors;
    }

}
