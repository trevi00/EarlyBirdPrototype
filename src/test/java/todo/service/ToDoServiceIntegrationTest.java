package todo.service;

import bird.model.Bird;
import bird.message.BirdMessageManager;
import org.junit.jupiter.api.*;
import todo.model.ToDo;
import bird.point.PointManager;
import todo.repository.JdbcToDoRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ToDoServiceIntegrationTest {

    private static Connection conn;
    private ToDoService toDoService;

    private final String TEST_USERNAME = "test1";

    @BeforeAll
    static void connect() throws Exception {
        conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1",
                "overflow",
                "12345"
        );
    }

    @BeforeEach
    void setup() throws Exception {
        // Clean up old test data
        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM TODOS WHERE USERNAME = ?")) {
            pstmt.setString(1, TEST_USERNAME);
            pstmt.executeUpdate();
        }

        // Create service
        toDoService = new DefaultToDoService(
                new JdbcToDoRepository(conn),
                new PointManager()
        );
    }

    @Test
    void 할일_등록_조회_완료_통합테스트() {
        LocalDate today = LocalDate.now();

        ToDo todo = new ToDo(
                UUID.randomUUID().toString(),
                TEST_USERNAME,
                today,
                "통합 테스트 제목",
                "통합 테스트 내용",
                false
        );

        boolean saved = toDoService.add(todo);
        assertTrue(saved, "할일 등록에 실패했습니다");

        List<ToDo> list = toDoService.findByUsername(TEST_USERNAME);
        assertEquals(1, list.size());
        assertEquals("통합 테스트 제목", list.get(0).getTitle());

        toDoService.markAsDone(TEST_USERNAME, today);

        ToDo updated = toDoService.findTodayToDo(TEST_USERNAME);
        assertTrue(updated.isDone(), "할일 완료 처리가 반영되지 않았습니다");
    }

    @AfterAll
    static void close() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
