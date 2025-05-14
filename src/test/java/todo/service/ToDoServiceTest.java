package todo.service;

import bird.repository.PointManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import todo.model.ToDo;
import todo.repository.InMemoryToDoRepository;
import todo.repository.ToDoRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ToDoServiceTest {

    private ToDoService toDoService;
    private ToDoRepository repository;
    private PointManager pointManager;

    @BeforeEach
    void setUp() {
        repository = new InMemoryToDoRepository();
        pointManager = new PointManager();
        toDoService = new DefaultToDoService(repository, pointManager);
    }

    @Test
    void testSaveToDo() {
        ToDo todo = new ToDo("user1", LocalDate.now(), "할 일", "내용입니다");
        boolean saved = toDoService.saveToDo(todo);

        assertTrue(saved);
        assertEquals(10, pointManager.getTotalPoint(), "포인트는 10점이어야 합니다.");
    }

    @Test
    void testDuplicateSaveFails() {
        ToDo todo = new ToDo("user1", LocalDate.now(), "중복", "내용");
        toDoService.saveToDo(todo);

        boolean secondTry = toDoService.saveToDo(todo);
        assertFalse(secondTry, "같은 날에는 하나만 작성 가능해야 합니다.");
    }

    @Test
    void testMarkAsDone() {
        String username = "user2";
        LocalDate today = LocalDate.now();
        ToDo todo = new ToDo(username, today, "완료 테스트", "테스트");

        toDoService.saveToDo(todo);
        toDoService.markAsDone(username, today);

        ToDo result = toDoService.getTodayToDo(username);
        assertNotNull(result);
        assertTrue(result.isDone(), "완료 처리 후 done 상태는 true여야 합니다.");
    }
}
