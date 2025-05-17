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

    /**
     * 배너 메시지 표시 (상단 영역에 임시 추가)
     */
    @Override
    public void showBanner(String message) {
        if (parentFrame == null) {
            System.err.println("❌ parentFrame is null. Cannot show banner.");
            return;
        }

        Container content = parentFrame.getContentPane();

        // 레이아웃이 BorderLayout이 아닌 경우, 강제로 설정
        if (!(content.getLayout() instanceof BorderLayout)) {
            content.setLayout(new BorderLayout());
        }

        // 기존 배너 제거 (덮어쓰기 방지)
        for (Component comp : content.getComponents()) {
            if (comp instanceof BirdBanner) {
                content.remove(comp);
                break;
            }
        }

        // 새로운 배너 추가
        BirdBanner banner = new BirdBanner(birdIcon, message);
        content.add(banner, BorderLayout.NORTH);

        content.revalidate();
        content.repaint();

        // 일정 시간 후 자동 제거
        Timer timer = new Timer(2500, e -> {
            content.remove(banner);
            content.revalidate();
            content.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * 팝업 메시지 출력
     */
    @Override
    public void speak(String message) {
        if (parentFrame != null) {
            JOptionPane.showMessageDialog(parentFrame, "🐤 " + message);
        } else {
            JOptionPane.showMessageDialog(null, "🐤 " + message);
        }
    }
}
