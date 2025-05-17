package user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.model.User;
import user.repository.InMemoryUserRepository;
import user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setup() {
        UserRepository repo = new InMemoryUserRepository();
        userService = new UserService(repo);
    }

    @Test
    void 사용자_등록_성공() {
        User user = new User("test1", "pw123", "홍길동");
        boolean result = userService.register(user);

        assertTrue(result, "회원 등록이 성공해야 합니다.");
        User saved = userService.findByUsername("test1");
        assertNotNull(saved);
        assertEquals("홍길동", saved.getDisplayName());
    }

    @Test
    void 중복_사용자_등록_실패() {
        userService.register("test1", "pw123", "홍길동");
        boolean result = userService.register("test1", "pw456", "이순신");

        assertFalse(result, "중복된 아이디는 등록할 수 없어야 합니다.");
    }

    @Test
    void 로그인_성공() {
        userService.register("test1", "pw123", "홍길동");

        User loginUser = userService.login("test1", "pw123");
        assertNotNull(loginUser, "로그인에 성공해야 합니다.");
        assertEquals("홍길동", loginUser.getDisplayName());
    }

    @Test
    void 로그인_실패_비밀번호_불일치() {
        userService.register("test1", "pw123", "홍길동");

        User loginUser = userService.login("test1", "wrongpw");
        assertNull(loginUser, "비밀번호가 다르면 로그인에 실패해야 합니다.");
    }

    @Test
    void 로그인_실패_존재하지_않는_사용자() {
        User loginUser = userService.login("ghost", "nopw");
        assertNull(loginUser, "존재하지 않는 사용자 로그인은 실패해야 합니다.");
    }
}
