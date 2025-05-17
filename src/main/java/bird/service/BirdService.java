package bird.service;

import bird.model.Bird;

public interface BirdService {
    boolean canEvolve(Bird bird);
    void evolve(Bird bird);

    int getPoint(String username);             // 포인트 조회 추가
    void usePoint(String username, int point); // 포인트 차감 추가
}
