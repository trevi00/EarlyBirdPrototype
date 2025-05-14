package bird.point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointManagerTest {

    private PointManager pointManager;

    @BeforeEach
    void setUp() {
        pointManager = new PointManager();
    }

    @Test
    void testInitialPointIsZero() {
        assertEquals(0, pointManager.getTotalPoint(), "초기 포인트는 0이어야 한다.");
    }

    @Test
    void testAddPoint() {
        pointManager.addPoint(10);
        assertEquals(10, pointManager.getTotalPoint());

        pointManager.addPoint(20);
        assertEquals(30, pointManager.getTotalPoint());
    }

    @Test
    void testAddNegativePoint() {
        pointManager.addPoint(-5);
        assertEquals(-5, pointManager.getTotalPoint());
    }
}
