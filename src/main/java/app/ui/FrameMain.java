package app.ui;

import app.context.EarlyBirdContext;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

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

        // 메인 메뉴 패널 생성
        MainMenuPanel mainPanel = new MainMenuPanel(context);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);

        // 상단 고정 배너 포함한 전체 레이아웃 구성
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(context.getBannerPanel(), BorderLayout.NORTH);  // ✅ context에서 가져온 bannerPanel
        wrapper.add(scrollPane, BorderLayout.CENTER);

        setContentPane(wrapper);
        setVisible(true);
    }

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
