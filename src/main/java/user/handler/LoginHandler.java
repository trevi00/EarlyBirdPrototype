package user.handler;

import user.model.User;
import user.service.UserService;
import user.session.SessionManager;

public class LoginHandler {

    private final UserService userService;

    public LoginHandler(UserService userService) {
        this.userService = userService;
    }

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
