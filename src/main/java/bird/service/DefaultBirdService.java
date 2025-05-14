package bird.service;

import bird.model.Bird;

/**
 * [DefaultBirdService]
 * - BirdService 기본 구현체
 */
public class DefaultBirdService implements BirdService {

    private StageEvolutionPolicy evolutionPolicy;

    public DefaultBirdService(StageEvolutionPolicy evolutionPolicy) {
        this.evolutionPolicy = evolutionPolicy;
    }

    @Override
    public boolean canEvolve(Bird bird) {
        return evolutionPolicy.canEvolve(bird);
    }

    @Override
    public void evolve(Bird bird) {
        evolutionPolicy.evolve(bird);
    }
}
