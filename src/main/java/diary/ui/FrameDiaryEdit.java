package diary.ui;

import diary.handler.DiaryHandler;
import diary.model.Diary;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * [FrameDiaryEdit]
 * - 선택한 일기의 내용을 수정할 수 있는 화면
 * - 제목, 날씨, 내용 필드 편집 후 저장 가능
 */
public class FrameDiaryEdit extends JFrame {

    private final DiaryHandler diaryHandler;
    private final LocalDate diaryDate;

    private JTextField titleField;
    private JTextField weatherField;
    private JTextArea contentArea;

    /**
     * 생성자
     * @param diaryHandler 일기 저장 처리 핸들러
     * @param diaryDate 수정할 일기의 날짜
     */
    public FrameDiaryEdit(DiaryHandler diaryHandler, LocalDate diaryDate) {
        this.diaryHandler = diaryHandler;
        this.diaryDate = diaryDate;

        setTitle("일기 수정");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        loadDiaryContent();

        setVisible(true);
    }

    /**
     * UI 구성: 제목, 날씨, 내용 입력 필드 + 버튼
     */
    private void initUI() {
        // 상단 날짜 라벨
        JLabel dateLabel = new JLabel("날짜: " + diaryDate.toString(), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Serif", Font.BOLD, 16));
        add(dateLabel, BorderLayout.NORTH);

        // 입력 패널
        JPanel inputPanel = new JPanel(new BorderLayout());

        // 제목 입력
        titleField = new JTextField();
        titleField.setBorder(BorderFactory.createTitledBorder("제목"));
        inputPanel.add(titleField, BorderLayout.NORTH);

        // 날씨 입력
        weatherField = new JTextField();
        weatherField.setBorder(BorderFactory.createTitledBorder("날씨"));
        inputPanel.add(weatherField, BorderLayout.CENTER);

        // 내용 입력
        contentArea = new JTextArea();
        contentArea.setBorder(BorderFactory.createTitledBorder("내용"));
        add(new JScrollPane(contentArea), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.WEST);

        // 버튼 영역
        JPanel buttonPanel = new JPanel();
        JButton btnSave = new JButton("저장");
        JButton btnCancel = new JButton("취소");

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // 저장 버튼 동작
        btnSave.addActionListener(e -> {
            String title = titleField.getText().trim();
            String weather = weatherField.getText().trim();
            String content = contentArea.getText().trim();

            if (title.isEmpty() || content.isEmpty()) {
                JOptionPane.showMessageDialog(this, "제목과 내용을 모두 입력해주세요.");
                return;
            }

            diaryHandler.saveDiary(diaryDate, weather, title, content);
            JOptionPane.showMessageDialog(this, "일기가 수정되었습니다.");
            dispose(); // 창 닫기
        });

        // 취소 버튼
        btnCancel.addActionListener(e -> dispose());
    }

    /**
     * 기존 일기 내용을 불러와 입력 필드에 표시
     */
    private void loadDiaryContent() {
        Diary diary = diaryHandler.getDiaryByDate(diaryDate);
        if (diary == null) {
            JOptionPane.showMessageDialog(this, "일기 내용을 불러올 수 없습니다.");
            dispose();
            return;
        }

        titleField.setText(diary.getTitle());
        weatherField.setText(diary.getWeather());
        contentArea.setText(diary.getContent());
    }
}