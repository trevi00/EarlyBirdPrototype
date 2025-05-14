package bird.repository;

public interface PointRepository {
    int findTotalPointByUserId(String userId);
    void saveOrUpdate(String userId, int totalPoint);
}

