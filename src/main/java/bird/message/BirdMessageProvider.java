package bird.message;

import bird.model.BirdStage;

import java.util.*;

/**
 * [BirdMessageProvider]
 * - 새가 상태에 따라 좋은 말을 랜덤으로 해주는 기능
 */
public class BirdMessageProvider {

    private final Map<BirdStage, List<String>> messages = new HashMap<>();

    public BirdMessageProvider() {
        initializeMessages();
    }

    private void initializeMessages() {
        messages.put(BirdStage.EGG, Arrays.asList(
                "곧 세상에 나올 준비가 되었어요!",
                "안에서 열심히 크고 있어요!"
        ));
        messages.put(BirdStage.BABY, Arrays.asList(
                "첫 걸음을 내딛었어요!",
                "세상이 신기해 보여요!"
        ));
        messages.put(BirdStage.CHILD, Arrays.asList(
                "날개를 펼치는 연습을 하고 있어요!",
                "더 높이 날 수 있을 것 같아요!"
        ));
        messages.put(BirdStage.ADULT, Arrays.asList(
                "자유롭게 하늘을 날아올라요!",
                "이제 어디든 갈 수 있어요!"
        ));
    }

    /**
     * 현재 BirdStage에 맞는 랜덤 메시지를 반환
     */
    public String getMessage(BirdStage stage) {
        List<String> stageMessages = messages.get(stage);
        if (stageMessages == null || stageMessages.isEmpty()) {
            return "힘내세요! 응원합니다!";
        }
        Random random = new Random();
        return stageMessages.get(random.nextInt(stageMessages.size()));
    }
}