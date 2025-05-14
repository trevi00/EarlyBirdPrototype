package attendance.ui;

import attendance.handler.AttendanceHandler;
import attendance.service.AttendanceService;
import bird.model.Bird;
import bird.point.PointManager;
import bird.service.BirdService;
import bird.message.BirdMessageProvider;
import bird.message.BirdMessageManager;
import bird.ui.FrameBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

/**
 * [FrameAttendance]
 * - 출석 체크 화면
 * - 출석 버튼 클릭 → AttendanceHandler에게 위임
 */
public class FrameAttendance extends JFrame {

    private AttendanceHandler attendanceHandler;
    private JButton btnCheck;  // 출석 체크 버튼

    public FrameAttendance(AttendanceService attendanceService,
                           PointManager pointManager,
                           Bird bird,
                           BirdService birdService,
                           BirdMessageProvider birdMessageProvider,
                           BirdMessageManager messageManager // ✅ 추가됨
    ) {
        setTitle("출석 체크");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        // 새 프레임 (현재 새 상태 보기용)
        FrameBird frameBird = new FrameBird(bird, birdService, messageManager); // ✅ 새 메시지 매니저 전달

        // AttendanceHandler 생성
        attendanceHandler = new AttendanceHandler(
                attendanceService,
                pointManager,
                bird,
                birdService,
                birdMessageProvider,
                frameBird,
                messageManager // ✅ 전달
        );

        // 출석 체크 버튼
        btnCheck = new JButton("출석 체크하기");
        btnCheck.addActionListener((ActionEvent e) -> handleAttendance());
        add(btnCheck);

        setVisible(true);
    }

    private void handleAttendance() {
        String username = "gyeongsu"; // TODO: 로그인 연동
        LocalDate today = LocalDate.now();
        attendanceHandler.handleAttendance(this, username, today);
    }
}