package user.repository;

import user.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * [InMemoryUserRepository]
 * - 메모리 기반 사용자 저장소
 * - 회원가입 기능을 위한 저장, 중복 체크 포함
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> store = new HashMap<>();

    public InMemoryUserRepository() {
        // 기본 계정
        store.put("gyeongsu", new User("gyeongsu", "1234", "김경수"));
    }

    @Override
    public User findByUsername(String username) {
        return store.get(username);
    }

    @Override
    public void save(User user) {
        store.put(user.getUsername(), user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return store.containsKey(username);
    }
}