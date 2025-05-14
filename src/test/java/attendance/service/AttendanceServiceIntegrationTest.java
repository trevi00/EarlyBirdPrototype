package attendance.service;

import attendance.repository.AttendanceRepository;
import attendance.repository.JdbcAttendanceRepository;
import bird.repository.PointManager;
import config.DatabaseConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceIntegrationTest {

    private AttendanceService attendanceService;
    private AttendanceRepository repository;
    private PointManager pointManager;

    private String uniqueUsername;

    @BeforeEach
    void setUp() {
        Connection conn = DatabaseConfig.getConnection();
        assertNotNull(conn, "DB 연결 실패");

        repository = new JdbcAttendanceRepository(conn);
        pointManager = new PointManager();
        attendanceService = new DefaultAttendanceService(repository, pointManager);

        // 고유 사용자 이름 (타임스탬프 기반)
        uniqueUsername = "integration_" + System.currentTimeMillis();

        // 혹시 모를 이전 테스트 데이터 정리
        clearTestAttendance(conn);
    }

    private void clearTestAttendance(Connection conn) {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM ATTENDANCE WHERE USERNAME LIKE 'integration_%'")) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testFirstAttendanceSuccess() {
        boolean result = attendanceService.checkAttendance(uniqueUsername, LocalDate.now());
        assertTrue(result, "첫 출석은 성공해야 합니다.");
    }

    @Test
    void testDuplicateAttendanceFails() {
        LocalDate today = LocalDate.now();

        assertTrue(attendanceService.checkAttendance(uniqueUsername, today),
                "첫 출석은 성공해야 합니다.");
        assertFalse(attendanceService.checkAttendance(uniqueUsername, today),
                "같은 날짜의 두 번째 출석은 실패해야 합니다.");
    }

    @Test
    void testPointAddedOnAttendance() {
        attendanceService.checkAttendance(uniqueUsername, LocalDate.now());
        assertEquals(10, pointManager.getTotalPoint(),
                "출석 시 포인트 10점이 적립되어야 합니다.");
    }
}
