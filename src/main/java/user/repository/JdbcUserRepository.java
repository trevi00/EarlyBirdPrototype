package user.repository;

import user.model.User;

import java.sql.*;

/**
 * [JdbcUserRepository]
 * - USERS 테이블과 연동되는 JDBC 기반 저장소
 * - user_id(PK)를 포함하여 User 객체로 반환하도록 개선됨
 */
public class JdbcUserRepository implements UserRepository {

    private final Connection conn;

    public JdbcUserRepository(Connection conn) {
        this.conn = conn;
    }

    /**
     * [로그인용 사용자 조회]
     * - username으로 조회하고, user_id 포함된 User 객체 반환
     */
    @Override
    public User findByUsername(String username) {
        String sql = "SELECT user_id, username, password, display_name FROM USERS WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // ✅ user_id 포함한 생성자로 변경됨
                return new User(
                        rs.getString("user_id"),       // UUID
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("display_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * [회원가입 처리]
     * - user_id는 서버(Java)에서 UUID로 생성해서 넣어야 함
     */
    @Override
    public void save(User user) {
        String sql = "INSERT INTO USERS (user_id, username, password, display_name) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUserId());     // ✅ UUID
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPassword());
            pstmt.setString(4, user.getDisplayName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * [아이디 중복 검사]
     */
    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM USERS WHERE username = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
