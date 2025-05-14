package todo.repository;

import todo.model.ToDo;

import java.time.LocalDate;
import java.util.List;

public interface ToDoRepository {

    void save(ToDo todo);

    ToDo findByUsernameAndDate(String username, LocalDate date);

    List<ToDo> findAllByUsername(String username);

    void delete(String username, LocalDate date);

    boolean exists(String username, LocalDate date);

    // ✅ 추가
    void markAsDone(String username, LocalDate date);
}