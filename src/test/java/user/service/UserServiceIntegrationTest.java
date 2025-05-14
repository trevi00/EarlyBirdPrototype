package user.service;

import config.DatabaseConfig;
import org.junit.jupiter.api.*;
import user.model.User;
import user.repository.JdbcUserRepository;
import user.repository.UserRepository;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceIntegrationTest {

    private static UserService userService;
    private static final String TEST_USERNAME = "integration_test_user";

    @BeforeAll
    static void init() {
        Connection conn = DatabaseConfig.getConnection();
        assertNotNull(conn, "DB 연결 실패");
        UserRepository repo = new JdbcUserRepository(conn);
        userService = new UserService(repo);
    }

    @Test
    @Order(1)
    void 회원가입_성공() {
        boolean result = userService.register(TEST_USERNAME, "pass123", "테스트유저");
        assertTrue(result, "회원가입이 성공해야 합니다.");
    }

    @Test
    @Order(2)
    void 로그인_성공() {
        User user = userService.login(TEST_USERNAME, "pass123");
        assertNotNull(user, "로그인 성공해야 함");
        assertEquals("테스트유저", user.getDisplayName());
    }

    @Test
    @Order(3)
    void 로그인_실패_비밀번호_틀림() {
        User user = userService.login(TEST_USERNAME, "wrongpass");
        assertNull(user, "비밀번호 틀리면 로그인 실패해야 함");
    }
}
