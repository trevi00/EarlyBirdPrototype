package bird.service;

import bird.model.Bird;
import bird.model.BirdStage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirdServiceTest {

    private Bird bird;
    private BirdService birdService;

    @BeforeEach
    void setUp() {
        bird = new Bird();
        StageEvolutionPolicy evolutionPolicy = new StageEvolutionPolicy();
        birdService = new DefaultBirdService(evolutionPolicy);
    }

    @Test
    void testCanEvolveFalseInitially() {
        assertFalse(birdService.canEvolve(bird), "처음 생성된 새는 진화할 수 없어야 한다.");
    }

    @Test
    void testCanEvolveAfterPointAdded() {
        bird.addPoint(10); // EGG 단계 필요 포인트 충족
        assertTrue(birdService.canEvolve(bird), "포인트가 충분하면 진화할 수 있어야 한다.");
    }

    @Test
    void testEvolveSuccessfully() {
        bird.addPoint(15); // EGG에서 BABY로 넘어갈 충분한 포인트

        assertTrue(birdService.canEvolve(bird));

        birdService.evolve(bird);

        assertEquals(BirdStage.BABY, bird.getStage(), "EGG -> BABY로 진화해야 한다.");
        assertEquals(0, bird.getPoint(), "진화 후 포인트는 초기화되어야 한다.");
    }
}
