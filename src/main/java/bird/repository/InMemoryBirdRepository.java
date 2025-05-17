package bird.repository;

import bird.model.Bird;

import java.util.HashMap;
import java.util.Map;

public class InMemoryBirdRepository implements BirdRepository {

    private final Map<String, Bird> storage = new HashMap<>();

    @Override
    public Bird findByUsername(String username) {
        return storage.getOrDefault(username, new Bird(username)); // 없으면 새로 생성
    }

    @Override
    public void save(Bird bird) {
        storage.put(bird.getUsername(), bird);
    }
}

