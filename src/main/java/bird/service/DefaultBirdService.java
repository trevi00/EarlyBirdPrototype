package bird.service;

import bird.model.Bird;
import bird.repository.BirdRepository;

/**
 * [DefaultBirdService]
 * - 새의 성장 로직 + 저장소 연동
 */
public class DefaultBirdService implements BirdService {

    private final StageEvolutionPolicy evolutionPolicy;
    private final BirdRepository birdRepository;

    public DefaultBirdService(StageEvolutionPolicy evolutionPolicy, BirdRepository birdRepository) {
        this.evolutionPolicy = evolutionPolicy;
        this.birdRepository = birdRepository;
    }

    @Override
    public boolean canEvolve(Bird bird) {
        return evolutionPolicy.canEvolve(bird);
    }

    @Override
    public void evolve(Bird bird) {
        if (evolutionPolicy.canEvolve(bird)) {
            bird.evolve();
            birdRepository.save(bird); // ✅ DB에 반영
        }
    }

    /**
     * 포인트 추가 시 저장도 함께 처리
     */
    public void addPoint(Bird bird, int amount) {
        bird.addPoint(amount);
        birdRepository.save(bird); // ✅ DB에 반영
    }

    /**
     * 사용자 ID로 새 로드
     */
    @Override
    public Bird loadBird(String userId) {
        return birdRepository.findByUserId(userId);
    }
}
