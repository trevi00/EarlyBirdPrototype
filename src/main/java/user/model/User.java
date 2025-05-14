package user.model;

/**
 * [User 모델 클래스]
 * - DB 기반 구조로 확장됨
 * - UUID 기반 userId를 포함
 */
public class User {
    private String userId;       // ✅ 추가: DB에서 조회된 UUID
    private String username;
    private String password;
    private String displayName;

    // ✅ 모든 필드를 포함한 생성자
    public User(String userId, String username, String password, String displayName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    // ✅ Getter 추가
    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }
}
