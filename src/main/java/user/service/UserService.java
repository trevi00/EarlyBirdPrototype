package user.service;

import user.model.User;
import user.repository.UserRepository;

public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    // User 객체로 회원 등록
    public boolean register(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            return false;
        }
        repository.save(user);
        return true;
    }

    // 개별 필드로 회원 등록
    public boolean register(String username, String password, String displayName) {
        return register(new User(username, password, displayName));
    }

    // 로그인: 일치하는 사용자 반환
    public User login(String username, String password) {
        User user = repository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
