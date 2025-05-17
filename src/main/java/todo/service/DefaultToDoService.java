package todo.service;

import bird.point.PointManager; // ✅ 패키지 경로 확인
import todo.model.ToDo;
import todo.repository.ToDoRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * [DefaultToDoService]
 * - 할 일 관리 비즈니스 로직 처리
 */
public class DefaultToDoService implements ToDoService {

    private final ToDoRepository repository;
    private final PointManager pointManager;

    public DefaultToDoService(ToDoRepository repository, PointManager pointManager) {
        this.repository = repository;
        this.pointManager = pointManager;
    }

    @Override
    public boolean add(ToDo todo) {
        if (repository.exists(todo.getUsername(), todo.getDate())) {
            return false;
        }

        boolean result = repository.save(todo);
        if (result) {
            pointManager.addPoint(5); // ✅ username 제거: bird.point.PointManager는 전역 포인트 누적 구조
        }
        return result;
    }

    @Override
    public boolean hasToDoToday(String username) {
        return repository.exists(username, LocalDate.now());
    }

    @Override
    public ToDo findTodayToDo(String username) {
        return repository.findByUsernameAndDate(username, LocalDate.now());
    }

    @Override
    public List<ToDo> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public void markAsDone(String username, LocalDate date) {
        repository.markAsDone(username, date);
    }
}
