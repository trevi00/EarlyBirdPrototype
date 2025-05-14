package bird.point;

/**
 * [PointManager]
 * - 전체 포인트를 관리하는 객체
 */
public class PointManager {

    private int totalPoint;

    public PointManager() {
        this.totalPoint = 0;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    /**
     * 포인트를 추가한다.
     */
    public void addPoint(int amount) {
        totalPoint += amount;
    }
}