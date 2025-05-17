package todo.ui;

import bird.message.BirdMessageManager;
import bird.model.Bird;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * [FrameToDo]
 * - 할일 작성 UI 프레임
 */
public class FrameToDo extends JFrame {

    public FrameToDo(ToDoService toDoService, Bird bird, BirdMessageManager birdMessageManager) {
        setTitle("오늘의 할일");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea();
        JButton btnSave = new JButton("할일 등록");

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(btnSave, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String username = bird.getUsername();
            String content = area.getText().trim();

            if (content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "내용을 입력하세요.");
                return;
            }

            // ✅ title, content는 동일하게 넣음. done = false
            ToDo todo = new ToDo(username, LocalDate.now(), content, content, false);
            boolean saved = toDoService.add(todo);

            if (saved) {
                JOptionPane.showMessageDialog(this, "할일이 등록되었습니다.");
                birdMessageManager.displayRandomMessage();  // ✅ 수정된 부분
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "이미 등록된 할일이 있습니다.");
            }
        });

        setVisible(true);
    }
}
