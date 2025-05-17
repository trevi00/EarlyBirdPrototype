package attendance.service;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcAttendanceStatsServiceTest {

    private static Connection conn;
    private JdbcAttendanceStatsService statsService;

    private final String TEST_USERNAME = "test1";

    @BeforeAll
    static void setupConnection() throws Exception {
        conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521/XEPDB1", "overflow", "12345"
        );
    }

    @BeforeEach
    void setup() throws Exception {
        statsService = new JdbcAttendanceStatsService(conn);

        try (PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM ATTENDANCE WHERE USERNAME = ?")) {
            pstmt.setString(1, TEST_USERNAME);
            pstmt.executeUpdate();
        }

        insertAttendance(TEST_USERNAME, LocalDate.of(2024, 5, 15));
        insertAttendance(TEST_USERNAME, LocalDate.of(2024, 5, 16));
        insertAttendance(TEST_USERNAME, LocalDate.of(2024, 6, 1));
    }

    private void insertAttendance(String username, LocalDate date) throws Exception {
        try (PreparedStatement pstmt = conn.prepareStatement(
                "INSERT INTO ATTENDANCE (USERNAME, ATTEND_DATE) VALUES (?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
        }
    }

    @Test
    void 출석_총_횟수_조회() {
        int total = statsService.getTotalAttendanceCount(TEST_USERNAME);  // ✅ 수정
        assertEquals(3, total);
    }

    @Test
    void 마지막_출석_날짜_조회() {
        LocalDate lastDate = statsService.getLastAttendanceDate(TEST_USERNAME);
        assertEquals(LocalDate.of(2024, 6, 1), lastDate);
    }

    @Test
    void 특정_월_출석_날짜_조회() {
        List<LocalDate> mayDates = statsService.getMonthlyAttendance(TEST_USERNAME, "2024-05");  // ✅ 타입 수정
        assertEquals(2, mayDates.size());
        assertTrue(mayDates.contains(LocalDate.of(2024, 5, 15)));
        assertTrue(mayDates.contains(LocalDate.of(2024, 5, 16)));
    }

    @AfterAll
    static void closeConnection() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
