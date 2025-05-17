package attendance.service;

import java.time.LocalDate;
import java.util.List;

/**
 * [AttendanceStatsService]
 * - 출석 통계 기능 제공 인터페이스
 */
public interface AttendanceStatsService {

    /**
     * 총 출석 횟수
     */
    int getTotalAttendanceCount(String username);

    /**
     * 마지막 출석 날짜
     */
    LocalDate getLastAttendanceDate(String username);

    /**
     * 전체 출석 날짜 목록 (최신순)
     */
    List<LocalDate> getAllAttendanceDates(String username);
}
