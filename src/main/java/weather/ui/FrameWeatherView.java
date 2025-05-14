package weather.ui;

import weather.model.Weather;
import weather.service.WeatherService;
import bird.message.BirdMessageManager;

import javax.swing.*;
import java.awt.*;

/**
 * [FrameWeatherView]
 * - 사용자로부터 도시명을 입력받아
 * - 오늘 날씨를 조회하고 결과를 화면에 표시
 * - 새 메시지 배너 및 팝업 응원 출력
 */
public class FrameWeatherView extends JFrame {

    private final WeatherService weatherService;
    private final BirdMessageManager messageManager;

    private JTextField cityField;
    private JButton btnSearch;
    private JLabel lblResult;

    public FrameWeatherView(WeatherService weatherService, BirdMessageManager messageManager) {
        this.weatherService = weatherService;
        this.messageManager = messageManager;

        setTitle("날씨 확인");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 입력부
        JPanel topPanel = new JPanel(new FlowLayout());

        cityField = new JTextField(15);
        btnSearch = new JButton("날씨 조회");

        topPanel.add(new JLabel("도시명 입력:"));
        topPanel.add(cityField);
        topPanel.add(btnSearch);

        add(topPanel, BorderLayout.NORTH);

        // 중앙 결과부
        lblResult = new JLabel("날씨 정보가 여기 표시됩니다.", SwingConstants.CENTER);
        add(lblResult, BorderLayout.CENTER);

        // 버튼 클릭 이벤트
        btnSearch.addActionListener(e -> searchWeather());

        setVisible(true);
    }

    /**
     * 도시명을 입력받아 날씨를 조회하고 결과를 표시한다.
     */
    private void searchWeather() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            JOptionPane.showMessageDialog(this, "도시명을 입력해주세요.");
            return;
        }

        Weather weather = weatherService.getTodayWeather(city);
        if (weather == null) {
            lblResult.setText("날씨 정보를 가져올 수 없습니다.");
        } else {
            lblResult.setText("<html>" +
                    "도시: " + weather.getCityName() + "<br>" +
                    "온도: " + weather.getTemperature() + "°C<br>" +
                    "상태: " + weather.getDescription() +
                    "</html>");

            // ✅ 새 배너 메시지 출력
            messageManager.say("오늘 날씨는 " + weather.getDescription() + "입니다.");

            // ✅ 새 응원 팝업
            messageManager.speakRandom();
        }
    }
}