package bird.service;

import bird.model.Bird;

/**
 * [BirdService 인터페이스]
 * - 새의 성장 관련 기능을 제공
 */
public interface BirdService {

    /**
     * 새가 성장할 수 있는지 확인한다.
     */
    boolean canEvolve(Bird bird);

    /**
     * 새를 성장시킨다.
     */
    void evolve(Bird bird);

    /**
     * 사용자 ID로 새 상태를 로드한다.
     */
    Bird loadBird(String userId);

    /**
     * 새의 포인트를 추가하고 DB에 반영한다.
     */
    void addPoint(Bird bird, int amount);
}
