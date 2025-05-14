package todo.ui;

import bird.message.BirdMessageManager;
import bird.model.Bird;
import bird.service.BirdService;
import todo.model.ToDo;
import todo.service.ToDoService;
import user.session.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * [FrameToDoCreate]
 * - 할 일(ToDo)을 작성하는 화면
 * - 하루에 하나만 작성 가능하며 작성 시 포인트 지급
 */
public class FrameToDoCreate extends JFrame {

    private final ToDoService toDoService;
    private final BirdMessageManager messageManager;
    private final Bird bird;
    private final BirdService birdService;

    public FrameToDoCreate(ToDoService toDoService, BirdMessageManager messageManager,
                           Bird bird, BirdService birdService) {
        this.toDoService = toDoService;
        this.messageManager = messageManager;
        this.bird = bird;
        this.birdService = birdService;

        setTitle("오늘의 할 일 작성");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 10));

        JTextField titleField = new JTextField();
        JTextArea contentArea = new JTextArea();
        contentArea.setLineWrap(true);

        inputPanel.add(new JLabel("제목:"), BorderLayout.NORTH);
        inputPanel.add(titleField, BorderLayout.CENTER);

        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.add(new JLabel("내용:"), BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        JButton btnSave = new JButton("저장하기");

        btnSave.addActionListener(e -> {
            String username = SessionManager.getCurrentUser().getUsername();
            String title = titleField.getText().trim();
            String content = contentArea.getText().trim();

            if (title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "제목과 내용을 모두 입력해주세요.");
                return;
            }

            ToDo todo = new ToDo(username, LocalDate.now(), title, content);
            boolean success = toDoService.saveToDo(todo);

            if (success) {
                // ✅ 포인트 적립 → DB 저장까지 포함
                birdService.addPoint(bird, 10);

                JOptionPane.showMessageDialog(this, "할 일이 저장되었습니다! 포인트 +10 🎉");
                messageManager.say("멋지게 오늘의 할 일을 작성했어요!");
                messageManager.speakRandom();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "오늘은 이미 할 일을 작성하셨어요!");
            }
        });

        add(inputPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(btnSave, BorderLayout.SOUTH);

        setVisible(true);
    }
}
