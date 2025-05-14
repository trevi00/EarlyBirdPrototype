package attendance.service;

import attendance.repository.AttendanceRepository;
import bird.point.PointManager;

import java.time.LocalDate;

/**
 * [DefaultAttendanceService]
 * - 출석 체크 기본 서비스 구현
 */
public class DefaultAttendanceService implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final PointManager pointManager;

    public DefaultAttendanceService(AttendanceRepository attendanceRepository, PointManager pointManager) {
        this.attendanceRepository = attendanceRepository;
        this.pointManager = pointManager;
    }

    @Override
    public boolean checkAttendance(String username, LocalDate date) {
        if (attendanceRepository.existsByDate(username, date)) {
            return false;
        }
        attendanceRepository.save(username, date);
        pointManager.addPoint(10);
        return true;
    }

    // ✅ 통계용으로 저장소 접근 허용
    public AttendanceRepository getRepository() {
        return attendanceRepository;
    }
}