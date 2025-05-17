package ui.component;

import javax.swing.*;
import java.awt.*;

public class BirdBanner extends JPanel {
    public BirdBanner(ImageIcon icon, String message) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel iconLabel = new JLabel(icon);
        JLabel textLabel = new JLabel(message);

        iconLabel.setPreferredSize(new Dimension(32, 32));
        textLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));

        add(iconLabel);
        add(textLabel);

        setBackground(new Color(255, 255, 204));
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }
}
