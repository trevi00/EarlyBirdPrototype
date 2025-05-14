package attendance.handler;

import attendance.service.AttendanceService;
import bird.model.Bird;
import bird.point.PointManager;
import bird.service.BirdService;
import bird.message.BirdMessageProvider;
import bird.message.BirdMessageManager;
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
            // 1. 포인트 적립
            pointManager.addPoint(10);
            bird.addPoint(10);

            // 2. 배너 메시지 출력
            messageManager.say("출석 완료! 오늘도 멋져요 😊");

            // 3. 랜덤 응원 메시지 팝업
            messageManager.speakRandom();

            // 4. 새 성장 가능성 확인 및 성장
            if (birdService.canEvolve(bird)) {
                birdService.evolve(bird);
                frameBird.refresh(); // 새 상태 업데이트

                // 성장 축하 메시지
                messageManager.say("🎉 축하합니다! 새가 성장했습니다! 현재 단계: " + bird.getStage().getName());
            }

            return true;
        } else {
            // 이미 출석한 경우
            JOptionPane.showMessageDialog(parentFrame, "이미 오늘 출석을 완료했습니다!");
            return false;
        }
    }
}
