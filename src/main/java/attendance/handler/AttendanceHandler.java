package attendance.handler;

import attendance.service.AttendanceService;
import bird.model.Bird;
import bird.repository.PointManager;
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
            // ✅ 1. 포인트 적립 + 새 포인트 DB 저장
            pointManager.addPoint(10);
            birdService.addPoint(bird, 10); // DB 반영 포함

            // 2. 배너 메시지 출력
            messageManager.say("출석 완료! 오늘도 멋져요 😊");

            // 3. 랜덤 응원 메시지 팝업
            messageManager.speakRandom();

            // ✅ 4. 성장 가능성 검사 → DB 저장 포함
            if (birdService.canEvolve(bird)) {
                birdService.evolve(bird); // 진화 + DB 저장
                frameBird.refresh();      // 새 UI 갱신
                messageManager.say("🎉 축하합니다! 새가 성장했습니다! 현재 단계: " + bird.getStage().getName());
            }

            return true;
        } else {
            JOptionPane.showMessageDialog(parentFrame, "이미 오늘 출석을 완료했습니다!");
            return false;
        }
    }
}
