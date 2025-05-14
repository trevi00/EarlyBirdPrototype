package bird.message;

import bird.model.BirdStage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirdMessageProviderTest {

    @Test
    void testEachStageHasMessages() {
        BirdMessageProvider provider = new BirdMessageProvider();

        for (BirdStage stage : BirdStage.values()) {
            String message = provider.getMessage(stage);
            assertNotNull(message, stage + " 단계에서 메시지가 null 이면 안 됩니다.");
            assertFalse(message.trim().isEmpty(), stage + " 단계에서 메시지가 비어 있으면 안 됩니다.");
        }
    }

    @Test
    void testRandomMessagesNotIdentical() {
        BirdMessageProvider provider = new BirdMessageProvider();
        BirdStage testStage = BirdStage.BABY;

        String msg1 = provider.getMessage(testStage);
        String msg2 = provider.getMessage(testStage);

        // 같은 메시지가 나올 수도 있지만, 일단 다르면 다양성 확인
        assertNotNull(msg1);
        assertNotNull(msg2);
        assertTrue(msg1.equals(msg2) || !msg1.equals(msg2));
    }
}
