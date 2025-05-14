package attendance.ui;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Set;

/**
 * [CalendarPanel]
 * - 특정 연월의 달력을 그리고, 출석일은 강조 표시한다.
 * - 출석일은 ● 또는 색상으로 구분
 */
public class CalendarPanel extends JPanel {

    private final YearMonth yearMonth;
    private final Set<LocalDate> attendanceDays;

    /**
     * 생성자
     * @param yearMonth 연/월 (ex. 2025-05)
     * @param attendanceDays 출석한 날짜 Set
     */
    public CalendarPanel(YearMonth yearMonth, Set<LocalDate> attendanceDays) {
        this.yearMonth = yearMonth;
        this.attendanceDays = attendanceDays;
        setLayout(new GridLayout(0, 7)); // 7열 (일~토)
        drawCalendar();
    }

    /**
     * 달력 그리기 메서드
     * - 요일 헤더 + 날짜 칸 구성
     */
    private void drawCalendar() {
        // 요일 헤더
        String[] weekDays = {"일", "월", "화", "수", "목", "금", "토"};
        for (String day : weekDays) {
            JLabel lbl = new JLabel(day, SwingConstants.CENTER);
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            add(lbl);
        }

        LocalDate firstDay = yearMonth.atDay(1);
        int emptyStart = firstDay.getDayOfWeek().getValue() % 7; // 일요일 = 0

        // 빈 칸 추가
        for (int i = 0; i < emptyStart; i++) {
            add(new JLabel(""));
        }

        int daysInMonth = yearMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = yearMonth.atDay(day);
            boolean attended = attendanceDays.contains(date);

            JLabel label = new JLabel(String.valueOf(day), SwingConstants.CENTER);

            if (attended) {
                label.setForeground(Color.WHITE);
                label.setOpaque(true);
                label.setBackground(new Color(60, 180, 75)); // 출석한 날짜는 초록색
                label.setToolTipText("출석함");
            }

            add(label);
        }
    }
}