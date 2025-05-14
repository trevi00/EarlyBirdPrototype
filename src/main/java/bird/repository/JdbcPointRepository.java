package bird.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPointRepository implements PointRepository {

    private final Connection conn;

    public JdbcPointRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int findTotalPointByUserId(String userId) {
        String sql = "SELECT total_point FROM POINTS WHERE user_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("total_point");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void saveOrUpdate(String userId, int totalPoint) {
        String sql = """
            MERGE INTO POINTS p
            USING (SELECT ? AS user_id FROM dual) s
            ON (p.user_id = s.user_id)
            WHEN MATCHED THEN
              UPDATE SET total_point = ?, updated_at = SYSDATE
            WHEN NOT MATCHED THEN
              INSERT (user_id, total_point, updated_at)
              VALUES (?, ?, SYSDATE)
            """;
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setInt(2, totalPoint);
            pstmt.setString(3, userId);
            pstmt.setInt(4, totalPoint);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

