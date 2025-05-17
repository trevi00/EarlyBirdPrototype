package todo.service;

import todo.model.ToDo;

import java.time.LocalDate;
import java.util.List;

/**
 * [ToDoService]
 * - 할 일 기능을 처리하는 서비스 인터페이스
 */
public interface ToDoService {

    boolean add(ToDo todo);

    boolean hasToDoToday(String username);

    ToDo findTodayToDo(String username);

    List<ToDo> findByUsername(String username);

    void markAsDone(String username, LocalDate date);
}
