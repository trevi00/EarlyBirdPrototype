package todo.repository;

import todo.model.ToDo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * [InMemoryToDoRepository]
 * - 테스트 및 임시 저장용 메모리 구현체
 */
public class InMemoryToDoRepository implements ToDoRepository {

    private final Map<String, Map<LocalDate, ToDo>> store = new HashMap<>();

    @Override
    public boolean save(ToDo todo) {
        store.computeIfAbsent(todo.getUsername(), k -> new HashMap<>())
                .put(todo.getDate(), todo);
        return true;
    }

    @Override
    public boolean exists(String username, LocalDate date) {
        return store.containsKey(username) && store.get(username).containsKey(date);
    }

    @Override
    public void markAsDone(String username, LocalDate date) {
        if (exists(username, date)) {
            ToDo original = store.get(username).get(date);
            ToDo updated = new ToDo(
                    original.getId(),
                    original.getUsername(),
                    original.getDate(),
                    original.getTitle(),
                    original.getContent(),
                    true
            );
            store.get(username).put(date, updated);
        }
    }

    @Override
    public ToDo findByUsernameAndDate(String username, LocalDate date) {
        if (!exists(username, date)) return null;
        return store.get(username).get(date);
    }

    @Override
    public List<ToDo> findByUsername(String username) {
        if (!store.containsKey(username)) return Collections.emptyList();
        return store.get(username).values().stream()
                .sorted(Comparator.comparing(ToDo::getDate).reversed())
                .collect(Collectors.toList());
    }
}
