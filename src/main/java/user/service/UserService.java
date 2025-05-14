package user.service;

import user.model.User;
import user.repository.UserRepository;

/**
 * [UserService]
 * - 사용자 로그인 및 회원가입 로직 처리
 */
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repo) {
        this.userRepository = repo;
    }

    /**
     * 로그인 시도
     */
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    /**
     * 회원가입 시도
     */
    public boolean register(String username, String password, String displayName) {
        if (userRepository.existsByUsername(username)) {
            return false; // 중복
        }

        User newUser = new User(username, password, displayName);
        userRepository.save(newUser);
        return true;
    }
}