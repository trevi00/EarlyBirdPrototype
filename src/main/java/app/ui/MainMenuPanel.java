package app.ui;

import app.context.EarlyBirdContext;
import bird.ui.FrameBird;
import coupon.ui.FrameCouponGallery;
import coupon.ui.FrameCouponStore;
import todo.ui.FrameToDo;
import todo.ui.FrameToDoList;

import javax.swing.*;
import java.awt.*;

/**
 * [MainMenuPanel]
 * - 기능 버튼들을 그룹화하고, UI를 정돈하여 UX 향상
 */
public class MainMenuPanel extends JPanel {

    public MainMenuPanel(EarlyBirdContext context) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        add(makeSectionLabel("📝 기록하기"));
        add(makeButton("✅ 출석하기", () -> {
            context.getBirdMessageManager().say("🧭 출석 화면으로 이동 중입니다...");
            context.showAttendanceFrame();
        }));

        add(makeButton("📔 일기 쓰기", () -> {
            context.getBirdMessageManager().say("🧭 일기 화면으로 이동 중입니다...");
            context.showDiaryFrame();
        }));

        add(makeButton("🗂️ 할 일 관리", () -> {
            context.getBirdMessageManager().say("🧭 할 일 작성 화면으로 이동 중입니다...");
            new FrameToDo(context.getToDoService(), context.bird, context.getBirdMessageManager()).setVisible(true);
        }));

        add(Box.createVerticalStrut(15));  // 간격

        add(makeSectionLabel("🔍 보기"));
        add(makeButton("📋 할 일 보기", () -> {
            context.getBirdMessageManager().say("🧭 할 일 확인 화면으로 이동 중입니다...");
            new FrameToDoList(
                    context.getToDoService(),
                    context.getCurrentUsername(),
                    context.getBirdMessageManager()
            ).setVisible(true);
        }));

        add(makeButton("🐣 새 보기", () -> {
            context.getBirdMessageManager().say("🧭 새 상태 화면으로 이동 중입니다...");
            new FrameBird(context.bird, context.birdService, context.getBirdMessageManager()).setVisible(true);
        }));

        add(makeButton("🎟️ 쿠폰 보관함", () -> {
            context.getBirdMessageManager().say("🧭 쿠폰 갤러리로 이동 중입니다...");
            new FrameCouponGallery(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
        }));

        add(Box.createVerticalStrut(15));  // 간격

        add(makeSectionLabel("💰 포인트"));
        add(makeButton("🛒 포인트 상점", () -> {
            context.getBirdMessageManager().say("🧭 포인트 상점으로 이동 중입니다...");
            new FrameCouponStore(context.getCouponController(), context.getCurrentUsername()).setVisible(true);
        }));
    }

    // 🔧 버튼 생성 유틸
    private JButton makeButton(String text, Runnable action) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(250, 40));
        btn.setBackground(new Color(240, 248, 255));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> action.run());
        return btn;
    }

    // 🔧 섹션 라벨 생성 유틸
    private JLabel makeSectionLabel(String title) {
        JLabel label = new JLabel(title);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        return label;
    }
}
