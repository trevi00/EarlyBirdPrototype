package attendance.repository;

import java.sql.*;
import java.time.LocalDate;

/**
 * [JdbcAttendanceRepository]
 * - Oracle DB에 출석 정보를 저장하고 조회하는 JDBC 구현체
 */
public class JdbcAttendanceRepository implements AttendanceRepository {

    private final Connection conn;

    public JdbcAttendanceRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean existsByDate(String username, LocalDate date) {
        String sql = "SELECT COUNT(*) FROM ATTENDANCE WHERE username = ? AND attend_date = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void save(String username, LocalDate date) {
        String sql = "INSERT INTO ATTENDANCE (username, attend_date) VALUES (?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("ORA-00001")) {
                System.out.println("이미 출석한 날짜입니다.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
