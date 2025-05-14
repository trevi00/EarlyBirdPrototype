package attendance.repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * [InMemoryAttendanceRepository]
 * - 메모리 기반 출석 기록 저장소
 */
public class InMemoryAttendanceRepository implements AttendanceRepository {

    private final Map<String, Set<LocalDate>> attendanceMap = new HashMap<>();

    @Override
    public boolean existsByDate(String username, LocalDate date) {
        return attendanceMap.containsKey(username) && attendanceMap.get(username).contains(date);
    }

    @Override
    public void save(String username, LocalDate date) {
        attendanceMap.putIfAbsent(username, new HashSet<>());
        attendanceMap.get(username).add(date);
    }
}