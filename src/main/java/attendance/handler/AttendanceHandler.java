package attendance.handler;

import attendance.service.AttendanceService;
import bird.message.BirdMessageManager;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.point.PointManager;
import bird.service.BirdService;
import bird.ui.FrameBird;

import javax.swing.*;
import java.time.LocalDate;

/**
 * [AttendanceHandler]
 * - 출석 체크 + 포인트 적립 + 새 성장 관리 + 메시지 출력
 */
public class AttendanceHandler {

    private final AttendanceService attendanceService;
    private final PointManager pointManager;
    private final Bird bird;
    private final BirdService birdService;
    private final BirdMessageProvider birdMessageProvider;
    private final FrameBird frameBird;
    private final BirdMessageManager messageManager;

    public AttendanceHandler(AttendanceService attendanceService,
                             PointManager pointManager,
                             Bird bird,
                             BirdService birdService,
                             BirdMessageProvider birdMessageProvider,
                             FrameBird frameBird,
                             BirdMessageManager messageManager) {
        this.attendanceService = attendanceService;
        this.pointManager = pointManager;
        this.bird = bird;
        this.birdService = birdService;
        this.birdMessageProvider = birdMessageProvider;
        this.frameBird = frameBird;
        this.messageManager = messageManager;
    }

    /**
     * 출석 체크를 수행한다.
     */
    public boolean handleAttendance(JFrame parentFrame, String username, LocalDate today) {
        boolean success = attendanceService.checkAttendance(username, today);

        if (success) {
            bird.addPoint(10);

            messageManager.say("출석 완료! 오늘도 멋져요 😊");
            messageManager.speakRandom();

            if (birdService.canEvolve(bird)) {
                birdService.evolve(bird);
                frameBird.refresh();

                messageManager.say("🎉 축하합니다! 새가 성장했습니다! 현재 단계: " + bird.getStage().getName());
            }

            return true;
        } else {
            JOptionPane.showMessageDialog(parentFrame, "이미 오늘 출석을 완료했습니다!");
            return false;
        }
    }
}
