package attendance.repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
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
            pstmt.setDate(2, java.sql.Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ 추가: 총 출석 수
    @Override
    public int countByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM ATTENDANCE WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ 추가: 마지막 출석일
    @Override
    public LocalDate findLastAttendanceDate(String username) {
        String sql = "SELECT MAX(attend_date) FROM ATTENDANCE WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date sqlDate = rs.getDate(1);
                return sqlDate != null ? sqlDate.toLocalDate() : null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ 추가: 전체 출석일 리스트
    @Override
    public List<LocalDate> findAllAttendanceDates(String username) {
        List<LocalDate> dates = new ArrayList<>();
        String sql = "SELECT attend_date FROM ATTENDANCE WHERE username = ? ORDER BY attend_date DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Date sqlDate = rs.getDate("attend_date");
                if (sqlDate != null) {
                    dates.add(sqlDate.toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
}
