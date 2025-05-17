package bird.message;

import bird.model.Bird;

/**
 * [BirdMessageManager]
 * - 새의 메시지를 제어하는 중앙 관리자
 * - 메시지 제공자(BirdMessageProvider)로부터 메시지를 받아 UI에 전달
 */
public class BirdMessageManager {

    private final Bird bird;
    private final BirdMessageProvider messageProvider;
    private final BirdMessageDisplayer displayer;

    /**
     * 생성자
     * @param bird 현재 새 객체 (단계, 포인트 등 상태 포함)
     * @param messageProvider 새 단계에 따른 메시지 제공자
     * @param displayer 메시지를 UI로 출력하는 책임자
     */
    public BirdMessageManager(Bird bird, BirdMessageProvider messageProvider, BirdMessageDisplayer displayer) {
        this.bird = bird;
        this.messageProvider = messageProvider;
        this.displayer = displayer;
    }

    /**
     * 랜덤 메시지를 즉시 말함 (예: 새 클릭 시)
     */
    public void speakRandom() {
        String message = messageProvider.getMessage(bird.getStage());
        displayer.speak(message);
    }

    /**
     * 지정된 메시지를 상단 배너로 보여줌 (예: 출석/일기/날씨 등)
     */
    public void say(String message) {
        displayer.showBanner(message);
    }

    /**
     * ✅ 새의 성장 단계에 맞는 랜덤 메시지를 상단 배너로 출력
     * (예: 할 일 등록 시 응원 메시지)
     */
    public void displayRandomMessage() {
        String message = messageProvider.getMessage(bird.getStage());
        displayer.showBanner(message);
    }
}
