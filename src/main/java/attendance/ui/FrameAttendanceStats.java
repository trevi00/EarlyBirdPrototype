package attendance.ui;

import attendance.service.AttendanceStatsService;
import user.model.User;
import user.session.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

/**
 * [FrameAttendanceStats]
 * - 로그인한 사용자의 특정 월 출석 정보를 달력 + 통계로 표시하는 UI
 * - CalendarPanel을 통해 달력 렌더링
 */
public class FrameAttendanceStats extends JFrame {

    private final AttendanceStatsService statsService;
    private final User user;
    private YearMonth selectedMonth;
    private JPanel calendarContainer;
    private JLabel lblSummary;

    /**
     * 생성자
     * @param statsService 출석 통계 계산 서비스
     */
    public FrameAttendanceStats(AttendanceStatsService statsService) {
        this.statsService = statsService;
        this.user = SessionManager.getCurrentUser();
        this.selectedMonth = YearMonth.now();

        setTitle("출석 통계");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        updateCalendar();

        setVisible(true);
    }

    /**
     * UI 초기 구성 (상단 월 선택, 중앙 달력, 하단 요약)
     */
    private void initUI() {
        // 상단: 월 선택
        JPanel topPanel = new JPanel();
        JButton btnPrev = new JButton("◀");
        JButton btnNext = new JButton("▶");
        JLabel lblMonth = new JLabel(selectedMonth.toString(), SwingConstants.CENTER);

        topPanel.add(btnPrev);
        topPanel.add(lblMonth);
        topPanel.add(btnNext);
        add(topPanel, BorderLayout.NORTH);

        // 중앙: 달력 위치
        calendarContainer = new JPanel(new BorderLayout());
        add(calendarContainer, BorderLayout.CENTER);

        // 하단: 요약 정보
        lblSummary = new JLabel("", SwingConstants.CENTER);
        lblSummary.setFont(new Font("Serif", Font.BOLD, 14));
        add(lblSummary, BorderLayout.SOUTH);

        // 이전 월
        btnPrev.addActionListener(e -> {
            selectedMonth = selectedMonth.minusMonths(1);
            lblMonth.setText(selectedMonth.toString());
            updateCalendar();
        });

        // 다음 월
        btnNext.addActionListener(e -> {
            selectedMonth = selectedMonth.plusMonths(1);
            lblMonth.setText(selectedMonth.toString());
            updateCalendar();
        });
    }

    /**
     * CalendarPanel을 새로 생성하여 중앙에 표시
     */
    private void updateCalendar() {
        calendarContainer.removeAll();

        Set<LocalDate> attendanceDays = statsService.getAttendanceDaysInMonth(user.getUsername(), selectedMonth);
        CalendarPanel panel = new CalendarPanel(selectedMonth, attendanceDays);
        calendarContainer.add(panel, BorderLayout.CENTER);

        // 요약 정보
        int attended = attendanceDays.size();
        int total = selectedMonth.lengthOfMonth();
        lblSummary.setText(String.format("출석일수: %d일 / %d일  (출석률 %.1f%%)", attended, total, (attended * 100.0 / total)));

        calendarContainer.revalidate();
        calendarContainer.repaint();
    }
}
