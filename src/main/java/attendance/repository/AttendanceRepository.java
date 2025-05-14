package attendance.repository;

import java.time.LocalDate;

/**
 * [AttendanceRepository]
 * - 출석 기록 저장소 인터페이스
 */
public interface AttendanceRepository {
    /**
     * 오늘 날짜로 출석했는지 여부를 반환한다.
     */
    boolean existsByDate(String username, LocalDate date);

    /**
     * 오늘 날짜로 출석을 저장한다.
     */
    void save(String username, LocalDate date);
}
