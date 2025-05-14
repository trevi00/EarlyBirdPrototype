package bird.message;

/**
 * [BirdMessageDisplayer]
 * - 새가 메시지를 출력하는 방식(UI)에 대한 인터페이스
 * - 배너 또는 팝업 등 다양한 표현 방식으로 확장 가능
 */
public interface BirdMessageDisplayer {

    /**
     * 화면 상단 배너로 메시지를 표시
     * @param message 새가 말할 메시지 (상황 기반)
     */
    void showBanner(String message);

    /**
     * 즉시 새가 말하도록 팝업으로 메시지를 출력
     * @param message 새의 말
     */
    void speak(String message);
}