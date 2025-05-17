package attendance.service;

import attendance.repository.AttendanceRepository;
import bird.point.PointManager;

import java.time.LocalDate;

/**
 * [DefaultAttendanceService]
 * - 출석 여부 확인 및 저장 로직 구현
 */
public class DefaultAttendanceService implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final PointManager pointManager;

    public DefaultAttendanceService(AttendanceRepository attendanceRepository, PointManager pointManager) {
        this.attendanceRepository = attendanceRepository;
        this.pointManager = pointManager;
    }

    @Override
    public boolean checkAttendance(String username, LocalDate today) {
        if (attendanceRepository.existsByDate(username, today)) {
            return false;
        }

        attendanceRepository.save(username, today);
        pointManager.addPoint(10); // 출석 시 포인트 적립
        return true;
    }
}
