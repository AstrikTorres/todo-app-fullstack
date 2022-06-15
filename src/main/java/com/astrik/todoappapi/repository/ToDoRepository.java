package com.astrik.todoappapi.repository;

import com.astrik.todoappapi.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByUserId(Long userId);
    void deleteByIdAndUserId(Long id, Long userId);

    Optional<ToDo> findByIdAndUserId(Long id, Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);
}
