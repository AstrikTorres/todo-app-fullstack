package com.astrik.todoappapi.service;

import com.astrik.todoappapi.entity.ToDo;
import com.astrik.todoappapi.entity.User;
import com.astrik.todoappapi.repository.ToDoRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Transactional
@Service
public class ToDoService {

    ToDoRepository toDoRepository;
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> reedTodosByUser(Long userId) {
        return toDoRepository.findByUserId(userId);
    }

    public List<ToDo> setTodosDefault(Long userId) {
        User user = new User(userId);
        ToDo toDo1 = new ToDo("Cortar cebolla", false, user);
        ToDo toDo2 = new ToDo("Llorar con la llorona", true, user);
        ToDo toDo3 = new ToDo("Hacer el curso de Spring Boot", true, user);
        ToDo toDo4 = new ToDo("Hacer el curso de React", true, user);

        List<ToDo> toDos = Arrays.asList(toDo1, toDo2, toDo3, toDo4);

        toDoRepository.saveAll(toDos);
        return toDos;
    }

    public ToDo saveTodo(ToDo toDo, Long userId) {
        toDo.setUser(new User(userId));
        return toDoRepository.save(toDo);
    }

    public List<ToDo> saveTodoList(List<ToDo> toDos, Long userId) {
        List<ToDo> toDosSaved = new ArrayList<>();
        toDos.forEach(toDo -> {
            toDo.setUser(new User(userId));
            toDoRepository.save(toDo);
            toDosSaved.add(toDo);
        });
        return toDosSaved;
    }

    public ToDo updateTodo(ToDo toDo, Long userId) {
        return toDoRepository.findById(toDo.getId())
                .map((item) -> {
                    if (item.getUser().getId().equals(userId)) {
                        toDo.setUser(new User(userId));
                        return toDoRepository.save(toDo);
                    }
                    else return null;
                }).orElseThrow(() -> new RuntimeException("id not found"));
    }

    public List<ToDo> updateTodoList(List<ToDo> toDos, Long userId) throws RuntimeException {
        List<ToDo> toDosUpdated = new ArrayList<>();
        toDos.forEach(toDo -> toDoRepository.findById(toDo.getId())
                .map((item) -> {
                    if (item.getUser().getId().equals(userId)) {
                        toDo.setUser(new User(userId));
                        return toDosUpdated.add(toDoRepository.save(toDo));
                    } else return null;
                }).orElseThrow(() -> new RuntimeException("todo id not found: " + toDo.getId()))
        );
        return toDosUpdated;
    }

    public boolean removeTodo(Long id, Long userId) {
        if (!toDoRepository.existsByIdAndUserId(id, userId)) return false;
        try {
            toDoRepository.deleteByIdAndUserId(id, userId);
            return !toDoRepository.existsByIdAndUserId(id, userId);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeTodoList(List<ToDo> toDos, Long userId) {
        toDos.forEach(toDo -> toDoRepository.findByIdAndUserId(toDo.getId(), userId)
                .map((t) -> {
                    if (t.getUser().getId().equals(userId))
                        toDoRepository.deleteByIdAndUserId(toDo.getId(), userId);
                    return true;
                }).orElseThrow(() -> new RuntimeException("id not found " + toDo.getId()))
        );
        return true;
    }
}
