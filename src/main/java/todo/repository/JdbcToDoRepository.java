package todo.repository;

import todo.model.ToDo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * [JdbcToDoRepository]
 * - Oracle DB 기반 구현체
 */
public class JdbcToDoRepository implements ToDoRepository {

    private final Connection conn;

    public JdbcToDoRepository(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(ToDo todo) {
        String sql = "INSERT INTO TODOS (id, username, todo_date, title, content, done) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, todo.getId());
            pstmt.setString(2, todo.getUsername());
            pstmt.setDate(3, Date.valueOf(todo.getDate()));
            pstmt.setString(4, todo.getTitle());
            pstmt.setString(5, todo.getContent());
            pstmt.setBoolean(6, todo.isDone());
            return pstmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean exists(String username, LocalDate date) {
        String sql = "SELECT COUNT(*) FROM TODOS WHERE username = ? AND todo_date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void markAsDone(String username, LocalDate date) {
        String sql = "UPDATE TODOS SET done = 1 WHERE username = ? AND todo_date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ToDo findByUsernameAndDate(String username, LocalDate date) {
        String sql = "SELECT * FROM TODOS WHERE username = ? AND todo_date = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setDate(2, Date.valueOf(date));
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ToDo(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getDate("todo_date").toLocalDate(),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getBoolean("done")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ToDo> findByUsername(String username) {
        String sql = "SELECT * FROM TODOS WHERE username = ? ORDER BY todo_date DESC";
        List<ToDo> list = new ArrayList<>();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ToDo todo = new ToDo(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getDate("todo_date").toLocalDate(),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getBoolean("done")
                );
                list.add(todo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
