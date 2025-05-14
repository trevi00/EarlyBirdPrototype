package bird.model;

import java.time.LocalDate;

/**
 * [Bird 모델]
 * - 새의 성장 상태를 관리
 * - 포인트를 누적하여 성장
 */
public class Bird {

    private BirdStage stage; // 현재 성장 단계
    private int point;       // 현재 포인트
    private LocalDate bornDate; // 태어난 날짜

    public Bird() {
        this.stage = BirdStage.EGG;
        this.point = 0;
        this.bornDate = LocalDate.now();
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

    /**
     * 포인트 추가
     */
    public void addPoint(int amount) {
        this.point += amount;
    }

    /**
     * 성장 가능한지 확인
     */
    public boolean canEvolve() {
        return stage.getNextStage() != null && point >= stage.getNeedPoint();
    }

    /**
     * 성장시키기
     */
    public void evolve() {
        if (canEvolve()) {
            this.stage = stage.getNextStage();
            this.point = 0; // 다음 성장을 위해 포인트 초기화
        }
    }
}