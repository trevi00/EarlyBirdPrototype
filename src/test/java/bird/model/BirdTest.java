package bird.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirdTest {

    @Test
    void testInitialStageIsEgg() {
        Bird bird = new Bird();
        assertEquals(BirdStage.EGG, bird.getStage());
    }

    @Test
    void testCannotEvolveIfNotEnoughPoints() {
        Bird bird = new Bird();
        bird.addPoint(5);
        assertFalse(bird.canEvolve(), "포인트가 부족하면 진화할 수 없어야 합니다.");
    }

    @Test
    void testCanEvolveWhenEnoughPoints() {
        Bird bird = new Bird();
        bird.addPoint(10); // EGG 단계의 필요 포인트
        assertTrue(bird.canEvolve(), "포인트가 충분하면 진화 가능해야 합니다.");
    }

    @Test
    void testEvolveProgression() {
        Bird bird = new Bird();
        bird.addPoint(10);
        assertTrue(bird.canEvolve());

        bird.evolve(); // EGG → BABY
        assertEquals(BirdStage.BABY, bird.getStage());
        assertEquals(0, bird.getPoint(), "진화 후 포인트는 초기화되어야 합니다.");

        bird.addPoint(30);
        bird.evolve(); // BABY → CHILD
        assertEquals(BirdStage.CHILD, bird.getStage());

        bird.addPoint(60);
        bird.evolve(); // CHILD → ADULT
        assertEquals(BirdStage.ADULT, bird.getStage());

        bird.addPoint(999); // ADULT는 마지막 단계
        bird.evolve();
        assertEquals(BirdStage.ADULT, bird.getStage(), "ADULT 이후에는 진화하지 않아야 합니다.");
    }
}
