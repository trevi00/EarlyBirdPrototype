package user.ui;

import app.context.EarlyBirdContext;
import user.handler.LoginHandler;
import user.ui.FrameLogin;
import user.ui.RegisterFrame;

import javax.swing.*;

public class UserUiTest {

    public static void main(String[] args) {
        // ✅ Swing UI는 EDT (Event Dispatch Thread)에서 실행
        SwingUtilities.invokeLater(() -> {
            EarlyBirdContext context = new EarlyBirdContext();

            // 1. 회원가입 UI 실행
            // new RegisterFrame(context.userService);

            // 2. 또는 로그인 UI 실행
            new FrameLogin(new LoginHandler(context.userService));
        });
    }
}

