package user.session;

import user.model.User;

/**
 * [SessionManager]
 * - 로그인 사용자 객체를 정적으로 보관
 * - userId 기반 기능들을 위해 확장 준비 완료
 */
public class SessionManager {

    private static User currentUser;

    public static void login(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // ✅ 자주 쓰이도록 userId 바로 반환하는 메서드도 추가 가능
    public static String getCurrentUserId() {
        return currentUser != null ? currentUser.getUserId() : null;
    }
}
