package user.repository;

import static org.junit.jupiter.api.Assertions.*;
import config.DatabaseConfig;
import user.model.User;
import user.repository.JdbcUserRepository;
import user.repository.UserRepository;
import user.service.UserService;

import java.sql.Connection;

public class JdbcUserRepositoryTest {

    public static void main(String[] args) {
        Connection conn = DatabaseConfig.getConnection();
        if (conn == null) {
            System.err.println("❌ DB 연결 실패. 테스트 불가");
            return;
        }

        UserRepository userRepository = new JdbcUserRepository(conn);
        UserService userService = new UserService(userRepository);

        String username = "testuser1";
        String password = "pass123";
        String displayName = "테스트 유저";

        System.out.println("✅ [회원가입 테스트]");
        if (userService.register(username, password, displayName)) {
            System.out.println("  ➤ 회원가입 성공!");
        } else {
            System.out.println("  ➤ 이미 존재하는 사용자. 가입 실패!");
        }

        System.out.println("✅ [로그인 테스트 - 성공]");
        User loginUser = userService.login(username, password);
        if (loginUser != null) {
            System.out.println("  ➤ 로그인 성공! 이름: " + loginUser.getDisplayName());
        } else {
            System.out.println("  ➤ 로그인 실패 (존재하지 않거나 비밀번호 오류)");
        }

        System.out.println("✅ [로그인 테스트 - 실패]");
        User wrongLogin = userService.login(username, "wrongpass");
        if (wrongLogin == null) {
            System.out.println("  ➤ 비밀번호 틀림. 로그인 실패 정상 처리됨");
        }
    }
}
