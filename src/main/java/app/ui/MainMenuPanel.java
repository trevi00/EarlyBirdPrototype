package app.ui;

import app.context.EarlyBirdContext;
import attendance.repository.AttendanceRepository;
import attendance.service.AttendanceStatsService;
import attendance.ui.FrameAttendance;
import attendance.ui.FrameAttendanceStats;
import bird.message.BirdMessageDisplayer;
import bird.message.BirdMessageManager;
import bird.ui.FrameBird;
import bird.ui.SwingBirdMessageDisplayer;
import todo.ui.FrameToDoCreate;
import todo.ui.FrameToDoList;
import weather.ui.FrameWeatherView;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * [MainMenuPanel]
 * - EarlyBird의 메인 기능 버튼들이 배치된 메인 메뉴 패널
 * - 각 버튼 클릭 시 해당 기능 화면으로 전환
 */
public class MainMenuPanel extends JPanel {

    private final EarlyBirdContext context;
    private final BirdMessageManager messageManager;

    public MainMenuPanel(JFrame parentFrame, EarlyBirdContext context) {
        this.context = context;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        add(Box.createVerticalStrut(30)); // 상단 여백

        // 🐦 새 메시지 매니저 설정
        ImageIcon birdIcon = new ImageIcon(getClass().getClassLoader().getResource("img/bird_icon.png"));
        BirdMessageDisplayer displayer = new SwingBirdMessageDisplayer(parentFrame, birdIcon);
        this.messageManager = new BirdMessageManager(
                context.bird,
                context.birdMessageProvider,
                displayer
        );

        // 출석 체크
        add(createButton("출석 체크", "attendance_icon.png", () ->
                new FrameAttendance(
                        context.attendanceService,
                        context.pointManager,
                        context.bird,
                        context.birdService,
                        context.birdMessageProvider,
                        messageManager
                )
        ));

        add(Box.createVerticalStrut(20));

        // 출석 통계 보기
        add(createButton("출석 통계 보기", "stats_icon.png", () -> {
            AttendanceRepository repo =
                    ((attendance.service.DefaultAttendanceService) context.attendanceService).getRepository();
            new FrameAttendanceStats(new AttendanceStatsService(repo));
        }));

        add(Box.createVerticalStrut(20));

        // 날씨 확인
        add(createButton("날씨 확인", "weather_icon.png", () ->
                new FrameWeatherView(context.weatherService, messageManager)
        ));

        add(Box.createVerticalStrut(20));

        // ✅ ToDo 작성
        add(createButton("오늘 할 일 작성", "todo_icon.png", () ->
                new FrameToDoCreate(context.toDoService, messageManager)
        ));

        add(Box.createVerticalStrut(20));

        // ✅ ToDo 목록 보기
        add(createButton("할 일 목록 보기", "todo_list_icon.png", () ->
                new FrameToDoList(context.toDoService, messageManager)
        ));

        add(Box.createVerticalStrut(20));

        // 새 보기
        add(createButton("새 보기", "bird_icon.png", () ->
                new FrameBird(context.bird, context.birdService, messageManager)
        ));

        add(Box.createVerticalGlue()); // 하단 여백
    }

    /**
     * 공통 버튼 생성 로직
     */
    private JButton createButton(String text, String iconName, Runnable action) {
        JButton button = new JButton(text, loadIcon(iconName));
        button.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(220, 50));
        button.setBackground(new Color(135, 206, 250));
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> action.run());
        return button;
    }

    /**
     * 아이콘 이미지 불러오기
     */
    private ImageIcon loadIcon(String name) {
        URL resource = getClass().getClassLoader().getResource("img/" + name);
        if (resource == null) {
            System.err.println("⚠️ 아이콘 파일 없음: img/" + name);
            return new ImageIcon();
        }
        Image img = new ImageIcon(resource).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
