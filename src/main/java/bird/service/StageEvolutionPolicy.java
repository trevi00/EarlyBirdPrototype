package bird.service;

import bird.model.Bird;

/**
 * [StageEvolutionPolicy]
 * - 새의 성장 조건과 성장 방법을 관리
 */
public class StageEvolutionPolicy {

    /**
     * 새가 성장할 수 있는지 판단한다.
     */
    public boolean canEvolve(Bird bird) {
        return bird.canEvolve();
    }

    /**
     * 새를 성장시킨다.
     */
    public void evolve(Bird bird) {
        if (bird.canEvolve()) {
            bird.evolve();
        }
    }
}