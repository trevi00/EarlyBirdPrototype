package ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * [BirdBanner]
 * - 상단에 표시되는 새의 메시지 배너 UI
 * - 새 아이콘과 함께 말풍선 형태의 메시지를 출력
 */
public class BirdBanner extends JPanel {

    public BirdBanner(ImageIcon birdIcon, String message) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setBackground(new Color(255, 250, 205)); // 밝은 노랑 배경
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        JLabel iconLabel = new JLabel();
        if (birdIcon != null) {
            iconLabel.setIcon(resizeIcon(birdIcon, 32, 32));
        }

        JLabel messageLabel = new JLabel("🐤 " + message);
        messageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        messageLabel.setForeground(Color.DARK_GRAY);

        add(iconLabel);
        add(messageLabel);
    }

    /**
     * 아이콘 크기 조절
     */
    private Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
