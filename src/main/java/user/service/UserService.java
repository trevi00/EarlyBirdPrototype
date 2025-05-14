package user.service;

import user.model.User;
import user.repository.UserRepository;

import java.util.UUID;

/**
 * [UserService]
 * - 사용자 로그인 및 회원가입 로직 처리
 * - UUID 기반 user_id 사용
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    /**
     * 로그인 시도
     * - user_id 포함된 User 객체를 반환받아 검증
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user; // ✅ 이미 userId 포함되어 있음
    }

    /**
     * 회원가입 시도
     * - UUID 기반 user_id를 직접 생성하여 User 객체 생성
     */
    public boolean register(String username, String password, String displayName) {
        if (userRepository.existsByUsername(username)) {
            return false; // 중복
        }

        // ✅ UUID 기반 userId 생성
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, username, password, displayName);
        userRepository.save(newUser);
        return true;
    }
}
