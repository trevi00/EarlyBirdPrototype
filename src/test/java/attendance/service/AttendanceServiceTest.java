package attendance.service;

import attendance.repository.AttendanceRepository;
import attendance.repository.InMemoryAttendanceRepository;
import bird.repository.PointManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceTest {

    private AttendanceService attendanceService;
    private AttendanceRepository repository;
    private PointManager pointManager;

    @BeforeEach
    void setUp() {
        repository = new InMemoryAttendanceRepository();
        pointManager = new PointManager();
        attendanceService = new DefaultAttendanceService(repository, pointManager);
    }

    @Test
    void testFirstAttendanceSucceeds() {
        boolean result = attendanceService.checkAttendance("user1", LocalDate.now());
        assertTrue(result, "첫 출석은 성공해야 합니다.");
    }

    @Test
    void testDuplicateAttendanceFails() {
        String username = "user2";
        LocalDate today = LocalDate.now();

        assertTrue(attendanceService.checkAttendance(username, today), "첫 출석은 성공해야 합니다.");
        assertFalse(attendanceService.checkAttendance(username, today), "같은 날짜의 두 번째 출석은 실패해야 합니다.");
    }

    @Test
    void testPointAddedOnAttendance() {
        String username = "user3";
        LocalDate today = LocalDate.now();

        attendanceService.checkAttendance(username, today);
        assertEquals(10, pointManager.getTotalPoint(), "출석 시 포인트 10점이 적립되어야 합니다.");
    }
}
