package attendance.service;

import attendance.repository.AttendanceRepository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

/**
 * [AttendanceStatsService]
 * - 사용자의 월간 출석 데이터를 계산하는 서비스
 * - AttendanceRepository를 통해 출석 기록을 조회
 */
public class AttendanceStatsService {

    private final AttendanceRepository attendanceRepository;

    /**
     * 생성자
     * @param attendanceRepository 출석 저장소 구현체
     */
    public AttendanceStatsService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    /**
     * 주어진 사용자와 연/월에 대해 출석한 날짜들을 반환한다.
     *
     * @param username 사용자 ID
     * @param yearMonth 연/월 (예: 2025-05)
     * @return 출석한 날짜(Set of LocalDate)
     */
    public Set<LocalDate> getAttendanceDaysInMonth(String username, YearMonth yearMonth) {
        Set<LocalDate> result = new HashSet<>();

        // 해당 월의 첫날부터 마지막 날까지 루프
        int daysInMonth = yearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            if (attendanceRepository.existsByDate(username, date)) {
                result.add(date);
            }
        }

        return result;
    }

    /**
     * 주어진 사용자와 연/월에 대해 출석한 총 일수를 반환한다.
     */
    public int getAttendanceCountInMonth(String username, YearMonth yearMonth) {
        return getAttendanceDaysInMonth(username, yearMonth).size();
    }
}