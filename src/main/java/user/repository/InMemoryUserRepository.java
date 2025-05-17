package user.repository;

import user.model.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> store = new HashMap<>();

    @Override
    public void save(User user) {
        store.put(user.getUsername(), user);
    }

    @Override
    public User findByUsername(String username) {
        return store.get(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return store.containsKey(username);
    }
}
