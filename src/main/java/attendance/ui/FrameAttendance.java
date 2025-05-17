package attendance.ui;

import attendance.handler.AttendanceHandler;
import attendance.service.AttendanceService;
import bird.message.BirdMessageManager;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.point.PointManager;
import bird.service.BirdService;
import bird.ui.FrameBird;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class FrameAttendance extends JFrame {

    public FrameAttendance(AttendanceService attendanceService,
                           PointManager pointManager,
                           Bird bird,
                           BirdService birdService,
                           BirdMessageProvider birdMessageProvider,
                           BirdMessageManager messageManager) {

        setTitle("출석 체크");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("오늘도 출석하고 포인트를 쌓아보세요!", SwingConstants.CENTER);
        JButton btnCheck = new JButton("출석하기");

        add(label, BorderLayout.CENTER);
        add(btnCheck, BorderLayout.SOUTH);

        // ✅ 인자 개수 맞춤
        FrameBird frameBird = new FrameBird(bird, birdService, messageManager);

        btnCheck.addActionListener(e -> {
            AttendanceHandler handler = new AttendanceHandler(
                    attendanceService,
                    pointManager,
                    bird,
                    birdService,
                    birdMessageProvider,
                    frameBird,
                    messageManager
            );

            boolean result = handler.handleAttendance(this, bird.getUsername(), LocalDate.now());
            if (result) {
                dispose();
            }
        });

        setVisible(true);
    }
}
