package user.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.model.User;
import user.repository.InMemoryUserRepository;
import user.repository.UserRepository;
import user.service.UserService;
import user.session.SessionManager;

import static org.junit.jupiter.api.Assertions.*;

public class LoginHandlerTest {

    private LoginHandler loginHandler;
    private UserService userService;

    @BeforeEach
    void setup() {
        UserRepository repo = new InMemoryUserRepository();
        userService = new UserService(repo);
        loginHandler = new LoginHandler(userService);
        SessionManager.logout(); // 매 테스트마다 초기화
    }

    @Test
    void 로그인_성공_세션_저장됨() {
        // given
        userService.register("test1", "pw123", "홍길동");

        // when
        boolean result = loginHandler.tryLogin("test1", "pw123");

        // then
        assertTrue(result, "로그인에 성공해야 함");
        assertNotNull(SessionManager.getCurrentUser(), "세션에 사용자 저장돼야 함");
        assertEquals("test1", SessionManager.getCurrentUser().getUsername());
    }

    @Test
    void 로그인_실패_비밀번호_틀림() {
        userService.register("test1", "pw123", "홍길동");

        boolean result = loginHandler.tryLogin("test1", "wrongpw");

        assertFalse(result);
        assertNull(SessionManager.getCurrentUser(), "세션에 아무도 없어야 함");
    }

    @Test
    void 로그인_실패_존재하지_않는_아이디() {
        boolean result = loginHandler.tryLogin("ghost", "nopw");

        assertFalse(result);
        assertNull(SessionManager.getCurrentUser(), "세션 없음");
    }
}
