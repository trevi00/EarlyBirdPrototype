package user.handler;

import user.model.User;
import user.service.UserService;
import user.session.SessionManager;

/**
 * [LoginHandler]
 * - 로그인 로직을 담당하는 핸들러
 * - 로그인 성공 시 SessionManager에 사용자 저장
 */
public class LoginHandler {

    private final UserService userService;

    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

    /**
     * 로그인 시도
     * @return 성공 시 true, 실패 시 false
     */
    public boolean tryLogin(String username, String password) {
        User user = userService.login(username, password);
        if (user != null) {
            SessionManager.login(user);
            return true;
        }
        return false;
    }

    public UserService getUserService() {
        return userService;
    }
}
