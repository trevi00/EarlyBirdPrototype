package bird.repository;

import bird.model.Bird;
import bird.model.BirdStage;

import java.sql.*;

public class JdbcBirdRepository implements BirdRepository {

    private final Connection conn;

    public JdbcBirdRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Bird findByUserId(String userId) {
        String sql = "SELECT * FROM BIRDS WHERE user_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                BirdStage stage = BirdStage.valueOf(rs.getString("stage"));
                int point = rs.getInt("point");
                Date bornDate = rs.getDate("born_date");

                return new Bird(userId, stage, point, bornDate.toLocalDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 없으면 새로 생성된 EGG 상태 반환
        Bird newBird = new Bird(userId);
        save(newBird);
        return newBird;
    }

    @Override
    public void save(Bird bird) {
        String sql = """
            MERGE INTO BIRDS b
            USING (SELECT ? AS user_id FROM dual) s
            ON (b.user_id = s.user_id)
            WHEN MATCHED THEN
                UPDATE SET stage = ?, point = ?, born_date = ?
            WHEN NOT MATCHED THEN
                INSERT (user_id, stage, point, born_date)
                VALUES (?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bird.getUserId());
            pstmt.setString(2, bird.getStage().name());
            pstmt.setInt(3, bird.getPoint());
            pstmt.setDate(4, Date.valueOf(bird.getBornDate()));

            pstmt.setString(5, bird.getUserId());
            pstmt.setString(6, bird.getStage().name());
            pstmt.setInt(7, bird.getPoint());
            pstmt.setDate(8, Date.valueOf(bird.getBornDate()));

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
