package bird.ui;

import bird.model.Bird;
import bird.service.BirdService;
import bird.message.BirdMessageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * [FrameBird]
 * - 새의 상태를 시각적으로 보여주는 화면 (그림 + 텍스트)
 * - 새를 클릭하면 메시지를 말함
 */
public class FrameBird extends JFrame {

    private final Bird bird;
    private final BirdService birdService;
    private final BirdMessageManager messageManager;
    private final BirdRenderer birdRenderer;
    private final JLabel lblBirdInfo;

    public FrameBird(Bird bird, BirdService birdService, BirdMessageManager messageManager) {
        this.bird = bird;
        this.birdService = birdService;
        this.messageManager = messageManager;

        setTitle("새 보기");
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 새 그림 그리는 패널
        birdRenderer = new BirdRenderer(bird);
        birdRenderer.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 커서 변경
        birdRenderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                messageManager.speakRandom(); // ✅ 클릭하면 새가 말함
            }
        });
        add(birdRenderer, BorderLayout.CENTER);

        // 새 상태 텍스트
        lblBirdInfo = new JLabel("", SwingConstants.CENTER);
        lblBirdInfo.setFont(new Font("Serif", Font.BOLD, 18));
        add(lblBirdInfo, BorderLayout.SOUTH);

        refresh();
        setVisible(true);
    }

    /**
     * 새 상태를 새로 고친다 (성장했을 때 호출)
     */
    public void refresh() {
        String info = "<html>" +
                "🐤 현재 단계: " + bird.getStage().getName() + "<br>" +
                "설명: " + bird.getStage().getDescription() + "<br>" +
                "포인트: " + bird.getPoint() + "점" +
                "</html>";
        lblBirdInfo.setText(info);
        birdRenderer.repaint();
    }
}