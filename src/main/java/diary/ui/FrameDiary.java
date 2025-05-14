package diary.ui;

import diary.model.Diary;
import diary.service.DiaryService;
import user.session.SessionManager;
import bird.message.BirdMessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

/**
 * [FrameDiary]
 * - 오늘 일기를 작성하는 화면
 * - DiaryService를 통해 일기 저장
 * - 저장 시 새의 배너 및 팝업 메시지를 출력
 */
public class FrameDiary extends JFrame {

    private final DiaryService diaryService;
    private final BirdMessageManager messageManager;

    private JTextField titleField;
    private JTextArea contentArea;
    private JButton btnSave;

    public FrameDiary(DiaryService diaryService, BirdMessageManager messageManager) {
        this.diaryService = diaryService;
        this.messageManager = messageManager;

        setTitle("일기 작성");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 날짜 표시
        JLabel dateLabel = new JLabel("오늘 날짜: " + LocalDate.now());
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(dateLabel, BorderLayout.NORTH);

        // 중앙 입력 영역
        JPanel centerPanel = new JPanel(new BorderLayout());

        titleField = new JTextField();
        titleField.setBorder(BorderFactory.createTitledBorder("제목"));
        centerPanel.add(titleField, BorderLayout.NORTH);

        contentArea = new JTextArea();
        contentArea.setBorder(BorderFactory.createTitledBorder("내용"));
        centerPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // 하단 저장 버튼
        btnSave = new JButton("일기 저장");
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveDiary();
            }
        });

        add(btnSave, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * "일기 저장" 버튼 클릭 시 호출
     */
    private void saveDiary() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        String weather = "맑음"; // TODO: 날씨 자동 연동
        String username = SessionManager.getCurrentUser().getUsername();

        if (title.isEmpty() || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목과 내용을 모두 입력해주세요.");
            return;
        }

        Diary diary = new Diary(username, LocalDate.now(), weather, title, content);
        diaryService.saveDiary(diary);

        // ✅ 새의 응원 배너와 팝업 메시지 출력
        messageManager.say("오늘 하루도 잘 기록했어요 😊");
        messageManager.speakRandom();

        JOptionPane.showMessageDialog(this, "일기가 저장되었습니다!");
        dispose(); // 저장 후 창 닫기
    }
}