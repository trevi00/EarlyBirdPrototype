package todo.service;

import todo.model.ToDo;

import java.time.LocalDate;
import java.util.List;

public interface ToDoService {

    boolean saveToDo(ToDo todo);

    ToDo getTodayToDo(String username);

    List<ToDo> getAllToDos(String username);

    void deleteToDo(String username, LocalDate date);

    // ✅ 추가
    void markAsDone(String username, LocalDate date);
}