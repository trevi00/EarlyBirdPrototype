package config;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    @Test
    void testConnection() {
        Connection conn = DatabaseConfig.getConnection();
        assertNotNull(conn, "DB 연결에 실패했습니다. 연결 객체가 null입니다.");
        System.out.println("✅ DB 연결 성공: " + conn);
    }

    @Test
    void testInsertSampleUser() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("INSERT INTO USERS (username, password, display_name) VALUES ('admin', 'admin123', '관리자')");
            System.out.println("✅ 관리자 계정 삽입 성공");
        } catch (Exception e) {
            fail("❌ 삽입 실패: " + e.getMessage());
        }
    }

    @Test
    void testTableCheck() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM user_tables")) {

            System.out.println("📋 현재 테이블 목록:");
            while (rs.next()) {
                System.out.println(" - " + rs.getString("table_name"));
            }
        } catch (Exception e) {
            fail("❌ 테이블 조회 실패: " + e.getMessage());
        }
    }


}
