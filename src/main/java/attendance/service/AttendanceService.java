package attendance.service;

import java.time.LocalDate;

/**
 * [AttendanceService]
 * - 출석 체크 비즈니스 로직 인터페이스
 */
public interface AttendanceService {
    /**
     * 오늘 날짜로 출석 체크를 수행한다.
     *
     * @return 성공하면 true (출석 성공), 실패하면 false (이미 출석함)
     */
    boolean checkAttendance(String username, LocalDate date);
}
