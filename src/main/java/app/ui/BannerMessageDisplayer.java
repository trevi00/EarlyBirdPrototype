package app.ui;

import bird.message.BirdMessageDisplayer;

import javax.swing.*;

/**
 * [BannerMessageDisplayer]
 * - 새 메시지를 UI에 출력하는 기본 구현체
 * - speak: 팝업창 표시
 * - showBanner: 상단 배너 형식 출력
 */
public class BannerMessageDisplayer implements BirdMessageDisplayer {

    @Override
    public void speak(String message) {
        JOptionPane.showMessageDialog(null, message, "🐤 새가 말해요!", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showBanner(String message) {
        JWindow banner = new JWindow();
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(16f));
        label.setOpaque(true);
        label.setBackground(new java.awt.Color(255, 255, 153));
        label.setBorder(BorderFactory.createLineBorder(java.awt.Color.DARK_GRAY));
        banner.getContentPane().add(label);
        banner.setSize(400, 50);
        banner.setLocationRelativeTo(null);
        banner.setVisible(true);
        new Timer(2000, e -> banner.dispose()).start();
    }
}
