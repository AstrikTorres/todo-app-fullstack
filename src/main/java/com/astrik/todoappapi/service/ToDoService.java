package com.astrik.todoappapi.service;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.repository.ToDoRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
public class ToDoService {

    private final Log LOG = LogFactory.getLog(ToDoService.class);

    ToDoRepository toDoRepository;

    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> reedTodos() {
        return toDoRepository.findAll();
    }

    @Transactional
    public List<ToDo> setTodosDefault() {
        ToDo toDo1 = new ToDo("Cortar cebolla", false);
        ToDo toDo2 = new ToDo("Llorar con la llorona", true);
        ToDo toDo3 = new ToDo("Hacer el curso de Spring Boot", true);
        ToDo toDo4 = new ToDo("Hacer el curso de React", true);

        List<ToDo> toDos = Arrays.asList(toDo1, toDo2, toDo3, toDo4);

        toDos.stream()
                .peek(user -> LOG.info("Insertando to do: " + user))
                .forEach(toDoRepository::save);
        return toDos;
    }

    public ToDo saveTodo(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public ToDo updateTodo(ToDo toDo) {
        return toDoRepository.findById(toDo.getId())
                .map((item) -> {
                    return toDoRepository.save(toDo);
                }).orElseThrow(() -> new RuntimeException("id not found"));
    }
}
