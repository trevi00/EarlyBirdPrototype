package bird.model;

import java.time.LocalDate;

/**
 * [Bird 모델]
 * - 사용자별로 관리되는 새의 상태
 * - 포인트를 누적하여 성장하며, DB 저장 가능
 */
public class Bird {

    private final String userId;           // 사용자 ID (PK)
    private BirdStage stage;              // 성장 단계
    private int point;                    // 현재 포인트
    private LocalDate bornDate;           // 태어난 날짜

    // 기본 생성자 (최초 생성 시)
    public Bird(String userId) {
        this.userId = userId;
        this.stage = BirdStage.EGG;
        this.point = 0;
        this.bornDate = LocalDate.now();
    }

    // DB 로딩용 생성자
    public Bird(String userId, BirdStage stage, int point, LocalDate bornDate) {
        this.userId = userId;
        this.stage = stage;
        this.point = point;
        this.bornDate = bornDate;
    }

    // ✅ Getter
    public String getUserId() {
        return userId;
    }

    public BirdStage getStage() {
        return stage;
    }

    public int getPoint() {
        return point;
    }

    public LocalDate getBornDate() {
        return bornDate;
    }

    // ✅ 포인트 추가
    public void addPoint(int amount) {
        this.point += amount;
    }

    // ✅ 성장 조건 검사
    public boolean canEvolve() {
        return stage.getNextStage() != null && point >= stage.getNeedPoint();
    }

    // ✅ 성장 진행
    public void evolve() {
        if (canEvolve()) {
            this.stage = stage.getNextStage();
            this.point = 0;
        }
    }

    // ✅ 강제 setter (필요시)
    public void setStage(BirdStage stage) {
        this.stage = stage;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setBornDate(LocalDate bornDate) {
        this.bornDate = bornDate;
    }
}
