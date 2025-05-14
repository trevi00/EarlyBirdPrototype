package diary.repository;

import diary.model.Diary;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * [JdbcDiaryRepository]
 * - Oracle DB에 일기를 저장/조회/삭제하는 JDBC 구현체
 */
public class JdbcDiaryRepository implements DiaryRepository {

    private final Connection conn;

    public JdbcDiaryRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Diary diary) {
        String sql = """
            MERGE INTO DIARY d
            USING (SELECT ? AS username, ? AS diary_date FROM dual) s
            ON (d.username = s.username AND d.diary_date = s.diary_date)
            WHEN MATCHED THEN
              UPDATE SET weather = ?, title = ?, content = ?
            WHEN NOT MATCHED THEN
              INSERT (username, diary_date, weather, title, content)
              VALUES (?, ?, ?, ?, ?)
            """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, diary.getUsername());
            pstmt.setDate(2, Date.valueOf(diary.getDate()));
            pstmt.setString(3, diary.getWeather());
            pstmt.setString(4, diary.getTitle());
            pstmt.setString(5, diary.getContent());
            pstmt.setString(6, diary.getUsername());
            pstmt.setDate(7, Date.valueOf(diary.getDate()));
            pstmt.setString(8, diary.getWeather());
            pstmt.setString(9, diary.getTitle());
            pstmt.setString(10, diary.getContent());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Diary> findAllByUsername(String username) {
        List<Diary> list = new ArrayList<>();
        String sql = "SELECT * FROM DIARY WHERE username = ? ORDER BY diary_date DESC";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                list.add(new Diary(
                        rs.getString("username"),
                        rs.getDate("diary_date").toLocalDate(),
                        rs.getString("weather"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Diary findByUsernameAndDate(String username, LocalDate date) {
        String sql = "SELECT * FROM DIARY WHERE username = ? AND diary_date = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Diary(
                        rs.getString("username"),
                        rs.getDate("diary_date").toLocalDate(),
                        rs.getString("weather"),
                        rs.getString("title"),
                        rs.getString("content")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteByUsernameAndDate(String username, LocalDate date) {
        String sql = "DELETE FROM DIARY WHERE username = ? AND diary_date = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}