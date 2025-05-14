package app.ui;

import app.context.EarlyBirdContext;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * [FrameMain]
 * - EarlyBird 메인 프레임 + 스크롤 패널 적용
 */
public class FrameMain extends JFrame {

    private EarlyBirdContext context;

    public FrameMain() {
        setGlobalUIStyle();

        setTitle("EarlyBird 🌅");
        setSize(500, 600);
        setMinimumSize(new Dimension(500, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 전체 시스템 구성
        this.context = new EarlyBirdContext();

        // 메인 메뉴 패널 생성 및 스크롤 적용
        MainMenuPanel mainPanel = new MainMenuPanel(this, context);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        setContentPane(scrollPane);

        setVisible(true);
    }

    /**
     * 전역 UI 스타일 설정
     * - 폰트 및 색상 커스터마이징
     */
    private void setGlobalUIStyle() {
        Font font = new Font("맑은 고딕", Font.PLAIN, 14);
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, font);
            }
        }

        UIManager.put("Button.background", new Color(135, 206, 250));
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Label.foreground", Color.DARK_GRAY);
    }

    public static void main(String[] args) {
        new FrameMain();
    }
}
