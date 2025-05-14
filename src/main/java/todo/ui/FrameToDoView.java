package todo.ui;

import bird.message.BirdMessageManager;
import todo.model.ToDo;
import todo.service.ToDoService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * [FrameToDoView]
 * - 오늘 할 일을 입력받고 저장하는 UI
 * - 하루에 하나만 작성 가능
 */
public class FrameToDoView extends JFrame {

    private final ToDoService toDoService;
    private final BirdMessageManager messageManager;

    public FrameToDoView(ToDoService toDoService, BirdMessageManager messageManager) {
        this.toDoService = toDoService;
        this.messageManager = messageManager;

        setTitle("오늘의 할 일 ✍️");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 상단 제목
        JLabel titleLabel = new JLabel("오늘 할 일 작성", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        // 입력 패널
        JTextField titleField = new JTextField();
        JTextArea contentArea = new JTextArea();
        contentArea.setLineWrap(true);

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(new JLabel("제목:"), BorderLayout.NORTH);
        inputPanel.add(titleField, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.add(new JLabel("내용:"), BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(contentPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 저장 버튼
        JButton saveButton = new JButton("저장하기");
        saveButton.setBackground(new Color(135, 206, 250));
        saveButton.addActionListener(e -> {
            String username = "gyeongsu"; // ✅ 나중에 SessionManager 적용
            String title = titleField.getText().trim();
            String content = contentArea.getText().trim();
            LocalDate today = LocalDate.now();

            if (title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "제목과 내용을 모두 입력해주세요.");
                return;
            }

            ToDo todo = new ToDo(username, today, title, content);
            boolean success = toDoService.saveToDo(todo);

            if (success) {
                JOptionPane.showMessageDialog(this, "✅ 저장 완료! 포인트 +10");
                messageManager.say("오늘의 할 일이 등록되었어요!");
                messageManager.speakRandom();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "오늘은 이미 할 일을 작성하셨어요!");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
