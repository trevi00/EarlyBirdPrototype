package bird.repository;

import user.session.SessionManager;

/**
 * [PointManager]
 * - 사용자의 포인트를 DB 기반으로 관리
 */
public class PointManager {

    private final PointRepository repository;

    public PointManager(PointRepository repository) {
        this.repository = repository;
    }

    /**
     * 현재 로그인한 사용자의 총 포인트 반환
     */
    public int getTotalPoint() {
        String userId = SessionManager.getCurrentUserId();
        return repository.findTotalPointByUserId(userId);
    }

    /**
     * 포인트를 추가하고 DB에 반영
     */
    public void addPoint(int amount) {
        String userId = SessionManager.getCurrentUserId();
        int current = repository.findTotalPointByUserId(userId);
        int updated = current + amount;
        repository.saveOrUpdate(userId, updated); // ✅ INSERT or UPDATE
    }
}
