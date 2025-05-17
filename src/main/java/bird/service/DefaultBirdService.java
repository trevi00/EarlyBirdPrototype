package bird.service;

import bird.model.Bird;
import bird.repository.BirdRepository;

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
        evolutionPolicy.evolve(bird);
    }

    @Override
    public int getPoint(String username) {
        Bird bird = birdRepository.findByUsername(username);
        return bird.getPoint();
    }

    @Override
    public void usePoint(String username, int point) {
        Bird bird = birdRepository.findByUsername(username);
        bird.addPoint(-point); // 음수로 전달하여 차감
        birdRepository.save(bird);
    }
}
