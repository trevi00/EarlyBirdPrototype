package user.repository;

import org.junit.jupiter.api.*;
import user.model.User;
import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcUserRepositoryTest {

    private JdbcUserRepository repository;
    private Connection conn;

    @BeforeAll
    void setupConnection() {
        conn = DatabaseConfig.getConnection();
        repository = new JdbcUserRepository(conn);
    }

    @BeforeEach
    void cleanup() throws Exception {
        // 테스트 시작 전 테이블 비우기
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM USERS");
        stmt.close();
    }

    @Test
    void 사용자_저장_및_조회() {
        User user = new User("test1", "pw123", "홍길동");
        repository.save(user);

        User found = repository.findByUsername("test1");
        assertNotNull(found);
        assertEquals("홍길동", found.getDisplayName());
    }

    @Test
    void 존재_확인_성공() {
        repository.save(new User("existUser", "pw", "이순신"));

        boolean exists = repository.existsByUsername("existUser");
        assertTrue(exists);
    }

    @Test
    void 존재_확인_실패() {
        boolean exists = repository.existsByUsername("ghost");
        assertFalse(exists);
    }

    @AfterAll
    void closeConnection() throws Exception {
        conn.close();
    }
}
