package attendance.service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * [JdbcAttendanceStatsService]
 * - JDBC 기반 출석 통계 서비스
 * - 총 출석 수, 마지막 출석일, 전체 출석 목록 제공
 * - 월별 출석도 부가적으로 지원
 */
public class JdbcAttendanceStatsService implements AttendanceStatsService {

    private final Connection conn;

    public JdbcAttendanceStatsService(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int getTotalAttendanceCount(String username) {
        String sql = "SELECT COUNT(*) FROM ATTENDANCE WHERE USERNAME = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public LocalDate getLastAttendanceDate(String username) {
        String sql = "SELECT MAX(ATTEND_DATE) FROM ATTENDANCE WHERE USERNAME = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date date = rs.getDate(1);
                return date != null ? date.toLocalDate() : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<LocalDate> getAllAttendanceDates(String username) {
        String sql = "SELECT ATTEND_DATE FROM ATTENDANCE WHERE USERNAME = ? ORDER BY ATTEND_DATE DESC";
        List<LocalDate> dates = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("ATTEND_DATE");
                if (date != null) {
                    dates.add(date.toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dates;
    }

    // ✅ 부가 기능: 특정 연/월의 출석일자 조회
    public List<LocalDate> getMonthlyAttendance(String username, String yearMonth) {
        String sql = """
            SELECT ATTEND_DATE
            FROM ATTENDANCE
            WHERE USERNAME = ? AND TO_CHAR(ATTEND_DATE, 'YYYY-MM') = ?
            ORDER BY ATTEND_DATE
        """;

        List<LocalDate> dates = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, yearMonth);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Date date = rs.getDate("ATTEND_DATE");
                if (date != null) {
                    dates.add(date.toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dates;
    }
}
