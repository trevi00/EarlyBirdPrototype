package attendance.service;

import attendance.repository.InMemoryAttendanceRepository;
import attendance.repository.AttendanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultAttendanceStatsServiceTest {

    private AttendanceStatsService statsService;
    private InMemoryAttendanceRepository repo;
    private static final String USER = "testUser";

    @BeforeEach
    void setup() {
        repo = new InMemoryAttendanceRepository();
        // 테스트용 샘플 데이터 삽입
        repo.save(USER, LocalDate.of(2025, 5, 1));
        repo.save(USER, LocalDate.of(2025, 5, 3));
        repo.save(USER, LocalDate.of(2025, 5, 10));

        statsService = new DefaultAttendanceStatsService(repo);
    }

    @Test
    void testTotalAttendance() {
        int total = statsService.getTotalAttendanceCount(USER);
        assertEquals(3, total, "총 출석 횟수는 3이어야 합니다.");
    }

    @Test
    void testLastAttendanceDate() {
        LocalDate last = statsService.getLastAttendanceDate(USER);
        assertEquals(LocalDate.of(2025, 5, 10), last, "마지막 출석일은 2025-05-10이어야 합니다.");
    }

    @Test
    void testAllAttendanceDates() {
        List<LocalDate> dates = statsService.getAllAttendanceDates(USER);
        assertEquals(3, dates.size(), "출석일 리스트 크기는 3이어야 합니다.");
        assertEquals(LocalDate.of(2025,5,10), dates.get(0));
        assertEquals(LocalDate.of(2025,5,3), dates.get(1));
        assertEquals(LocalDate.of(2025,5,1), dates.get(2));
    }

    @Test
    void testMonthlyAttendanceSubset() {
        // 이 테스트에서는 getMonthlyAttendance가 없으므로
        // findAllAttendanceDates로 대체 검증 예시
        List<LocalDate> dates = statsService.getAllAttendanceDates(USER);
        assertTrue(dates.contains(LocalDate.of(2025,5,3)));
        assertFalse(dates.contains(LocalDate.of(2025,6,1)));
    }
}
