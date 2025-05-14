package user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.model.User;
import user.repository.InMemoryUserRepository;

import static org.junit.jupiter.api.Assertions.*;

/**
 * [UserServiceTest]
 * - 로그인 및 회원가입 기능 단위 테스트
 */
class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(new InMemoryUserRepository());
    }

    @Test
    void 로그인_성공_테스트() {
        User user = userService.login("gyeongsu", "1234");
        assertNotNull(user);
        assertEquals("gyeongsu", user.getUsername());
    }

    @Test
    void 로그인_실패_잘못된_비밀번호() {
        User user = userService.login("gyeongsu", "wrong");
        assertNull(user);
    }

    @Test
    void 회원가입_성공_테스트() {
        boolean result = userService.register("newuser", "pass", "새 유저");
        assertTrue(result);

        User saved = userService.login("newuser", "pass");
        assertNotNull(saved);
        assertEquals("새 유저", saved.getDisplayName());
    }

    @Test
    void 회원가입_실패_중복아이디() {
        boolean result = userService.register("gyeongsu", "1234", "이미 있는 유저");
        assertFalse(result);
    }
}
