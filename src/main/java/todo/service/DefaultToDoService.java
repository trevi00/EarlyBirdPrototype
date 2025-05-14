package todo.service;

import bird.repository.PointManager;
import todo.model.ToDo;
import todo.repository.ToDoRepository;

import java.time.LocalDate;
import java.util.List;

public class DefaultToDoService implements ToDoService {

    private final ToDoRepository repository;
    private final PointManager pointManager;

    public DefaultToDoService(ToDoRepository repository, PointManager pointManager) {
        this.repository = repository;
        this.pointManager = pointManager;
    }

    @Override
    public boolean saveToDo(ToDo todo) {
        if (repository.exists(todo.getUsername(), todo.getDate())) {
            return false;
        }
        repository.save(todo);
        pointManager.addPoint(10);
        return true;
    }

    @Override
    public ToDo getTodayToDo(String username) {
        return repository.findByUsernameAndDate(username, LocalDate.now());
    }

    @Override
    public List<ToDo> getAllToDos(String username) {
        return repository.findAllByUsername(username);
    }

    @Override
    public void deleteToDo(String username, LocalDate date) {
        repository.delete(username, date);
    }

    // ✅ 추가
    @Override
    public void markAsDone(String username, LocalDate date) {
        repository.markAsDone(username, date);
    }
}
