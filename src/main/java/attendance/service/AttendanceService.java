package attendance.service;

import java.time.LocalDate;

public interface AttendanceService {
    boolean checkAttendance(String username, LocalDate today);
}
