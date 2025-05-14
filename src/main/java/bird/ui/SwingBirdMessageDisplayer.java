package bird.ui;

import bird.message.BirdMessageDisplayer;
import ui.component.BirdBanner;

import javax.swing.*;
import java.awt.*;

/**
 * [SwingBirdMessageDisplayer]
 * - Swing 기반 새 메시지 표현 클래스
 * - 배너와 팝업 모두 지원
 */
public class SwingBirdMessageDisplayer implements BirdMessageDisplayer {

    private final JFrame parentFrame;
    private final ImageIcon birdIcon;

    /**
     * 생성자
     * @param parentFrame 메시지를 띄울 대상 프레임
     * @param birdIcon 새의 프로필 이미지
     */
    public SwingBirdMessageDisplayer(JFrame parentFrame, ImageIcon birdIcon) {
        this.parentFrame = parentFrame;
        this.birdIcon = birdIcon;
    }

    @Override
    public void showBanner(String message) {
        if (parentFrame == null) {
            System.err.println("❌ parentFrame is null. Cannot show banner.");
            return;
        }

        Container content = parentFrame.getContentPane();

        // 레이아웃이 BorderLayout이 아닌 경우, 강제 설정
        if (!(content.getLayout() instanceof BorderLayout)) {
            content.setLayout(new BorderLayout());
        }

        BirdBanner banner = new BirdBanner(birdIcon, message);
        content.add(banner, BorderLayout.NORTH);

        content.revalidate();
        content.repaint();
    }

    @Override
    public void speak(String message) {
        if (parentFrame != null) {
            JOptionPane.showMessageDialog(parentFrame, "🐤 " + message);
        } else {
            JOptionPane.showMessageDialog(null, "🐤 " + message);
        }
    }
}
