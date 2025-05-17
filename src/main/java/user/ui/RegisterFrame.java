package user.ui;

import user.service.UserService;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    public RegisterFrame(UserService userService) {
        setTitle("회원가입");
        setSize(300, 250);
        setLayout(new GridLayout(5, 2));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField nameField = new JTextField();
        JButton btnRegister = new JButton("가입하기");

        add(new JLabel("아이디:"));
        add(usernameField);
        add(new JLabel("비밀번호:"));
        add(passwordField);
        add(new JLabel("닉네임:"));
        add(nameField);
        add(new JLabel(""));
        add(btnRegister);

        btnRegister.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String displayName = nameField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || displayName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.");
                return;
            }

            boolean success = userService.register(username, password, displayName);
            if (success) {
                JOptionPane.showMessageDialog(this, "회원가입 완료!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.");
            }
        });

        setVisible(true);
    }
}
