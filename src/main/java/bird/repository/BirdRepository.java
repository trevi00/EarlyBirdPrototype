// 📁 bird/repository/BirdRepository.java
package bird.repository;

import bird.model.Bird;

public interface BirdRepository {
    Bird findByUsername(String username);  // 새 조회
    void save(Bird bird);                  // 새 저장 (포인트 갱신 포함)
}
