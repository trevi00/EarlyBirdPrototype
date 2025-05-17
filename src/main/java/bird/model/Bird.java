package bird.model;

import java.time.LocalDate;

public class Bird {

    private String username;     // 🔹 사용자 이름 (새 소유자)
    private BirdStage stage;
    private int point;
    private LocalDate bornDate;

    public Bird(String username) {
        this.username = username;
        this.stage = BirdStage.EGG;
        this.point = 0;
        this.bornDate = LocalDate.now();
    }

    public String getUsername() {
        return username;
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

    public void addPoint(int amount) {
        this.point += amount;
    }

    public boolean canEvolve() {
        return stage.getNextStage() != null && point >= stage.getNeedPoint();
    }

    public void evolve() {
        if (canEvolve()) {
            this.stage = stage.getNextStage();
            this.point = 0;
        }
    }
}
