package bird.ui;

import bird.model.Bird;
import bird.model.BirdStage;

import javax.swing.*;
import java.awt.*;

/**
 * [BirdRenderer]
 * - 새의 이미지를 현재 성장 단계에 따라 그리는 컴포넌트
 */
public class BirdRenderer extends JPanel {

    private final Bird bird;

    public BirdRenderer(Bird bird) {
        this.bird = bird;
        setPreferredSize(new Dimension(200, 200));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        BirdStage stage = bird.getStage();
        String imagePath = "img/" + stage.name().toLowerCase() + ".png"; // ✅ 64x64 제거
        Image image = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource(imagePath));

        // 이미지가 있다면 중앙에 그리기
        if (image != null) {
            g.drawImage(image, 30, 30, 128, 128, this);
        } else {
            g.drawString("이미지를 불러올 수 없습니다.", 50, 100);
        }
    }
}