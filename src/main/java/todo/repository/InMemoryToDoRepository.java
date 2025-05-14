package todo.repository;

import todo.model.ToDo;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * [InMemoryToDoRepository]
 * - 메모리 기반 할 일 저장소
 * - 테스트 및 프로토타입용으로 사용
 */
public class InMemoryToDoRepository implements ToDoRepository {

    // username → 날짜 → ToDo
    private final Map<String, Map<LocalDate, ToDo>> store = new ConcurrentHashMap<>();

    @Override
    public void save(ToDo todo) {
        store
                .computeIfAbsent(todo.getUsername(), k -> new HashMap<>())
                .put(todo.getDate(), todo);
    }

    @Override
    public ToDo findByUsernameAndDate(String username, LocalDate date) {
        Map<LocalDate, ToDo> userToDos = store.get(username);
        if (userToDos == null) return null;
        return userToDos.get(date);
    }

    @Override
    public List<ToDo> findAllByUsername(String username) {
        Map<LocalDate, ToDo> userToDos = store.get(username);
        if (userToDos == null) return Collections.emptyList();
        return new ArrayList<>(userToDos.values());
    }

    @Override
    public void delete(String username, LocalDate date) {
        Map<LocalDate, ToDo> userToDos = store.get(username);
        if (userToDos != null) {
            userToDos.remove(date);
        }
    }

    @Override
    public boolean exists(String username, LocalDate date) {
        Map<LocalDate, ToDo> userToDos = store.get(username);
        return userToDos != null && userToDos.containsKey(date);
    }

    @Override
    public void markAsDone(String username, LocalDate date) {
        Map<LocalDate, ToDo> userToDos = store.get(username);
        if (userToDos != null && userToDos.containsKey(date)) {
            ToDo original = userToDos.get(date);
            // 완료 상태만 true로 바꾼 새 객체로 교체
            ToDo updated = new ToDo(
                    original.getUsername(),
                    original.getDate(),
                    original.getTitle(),
                    original.getContent(),
                    true
            );
            userToDos.put(date, updated);
        }
    }
}
