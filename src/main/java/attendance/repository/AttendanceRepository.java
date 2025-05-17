package attendance.repository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository {
    boolean existsByDate(String username, LocalDate date);
    void save(String username, LocalDate date);

    // ✅ 추가된 메서드들
    int countByUsername(String username);
    LocalDate findLastAttendanceDate(String username);
    List<LocalDate> findAllAttendanceDates(String username);
}
