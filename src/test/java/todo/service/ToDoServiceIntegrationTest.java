package todo.service;

import org.junit.jupiter.api.*;
import todo.model.ToDo;
import todo.repository.JdbcToDoRepository;
import todo.repository.ToDoRepository;
import bird.model.Bird;
import bird.repository.BirdRepository;
import bird.repository.JdbcBirdRepository;
import bird.service.BirdService;
import bird.service.DefaultBirdService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceIntegrationTest {

    private Connection conn;
    private ToDoService toDoService;
    private BirdService birdService;
    private Bird bird;

    @BeforeEach
    public void setUp() throws Exception {
        // 🔧 DB 연결 설정
        conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe", "your_id", "your_pw"
        );

        // 🐦 BirdService 준비 (test1 유저 기준)
        BirdRepository birdRepo = new JdbcBirdRepository(conn);
        birdService = new DefaultBirdService(birdRepo);
        bird = birdService.loadBird("test1");

        // 📋 ToDoService 구성
        ToDoRepository toDoRepo = new JdbcToDoRepository(conn);
        toDoService = new DefaultToDoService(toDoRepo, birdService, bird);
    }

    @Test
    public void testSaveToDo_shouldStoreToDoAndGivePoint() {
        // given
        String username = "test1";
        LocalDate today = LocalDate.now();
        ToDo todo = new ToDo(username, today, "JUnit 테스트 할 일", "DB 저장 잘 되는지 확인");

        // when
        boolean success = toDoService.saveToDo(todo);

        // then
        assertTrue(success, "할 일 저장이 성공해야 함");
        ToDo saved = toDoService.getTodayToDo(username);
        assertNotNull(saved, "DB에 저장된 할 일이 있어야 함");
        assertEquals("JUnit 테스트 할 일", saved.getTitle());

        System.out.println("✅ 저장된 할 일: " + saved.getTitle() + " / " + saved.getContent());
        System.out.println("✅ 현재 포인트: " + bird.getPoint());
    }

    @AfterEach
    public void cleanUp() throws Exception {
        conn.prepareStatement("DELETE FROM TODOS WHERE username = 'test1' AND todo_date = TO_DATE(SYSDATE, 'YYYY-MM-DD')").executeUpdate();
        conn.close();
    }
}
