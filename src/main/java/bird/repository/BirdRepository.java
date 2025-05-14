package bird.repository;

import bird.model.Bird;

public interface BirdRepository {

    /**
     * 사용자 ID로 새 상태를 조회한다.
     */
    Bird findByUserId(String userId);

    /**
     * 사용자 ID의 새 상태를 저장 또는 갱신한다.
     */
    void save(Bird bird);
}
