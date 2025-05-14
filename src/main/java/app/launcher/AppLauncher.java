package app.launcher;

import user.handler.LoginHandler;
import user.repository.JdbcUserRepository;
import user.service.UserService;
import user.ui.FrameLogin;
import config.DatabaseConfig;

import java.sql.Connection;

/**
 * [AppLauncher]
 * - DB 기반 로그인 화면을 실행하는 진입점 클래스
 * - 단독 실행 및 테스트 시 사용
 */
public class AppLauncher {
    public static void main(String[] args) {
        Connection conn = DatabaseConfig.getConnection();
        UserService userService = new UserService(new JdbcUserRepository(conn));
        LoginHandler loginHandler = new LoginHandler(userService);

        new FrameLogin(loginHandler);
    }

}
