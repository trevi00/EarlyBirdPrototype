package attendance.repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * [InMemoryAttendanceRepository]
 * - 메모리 기반으로 출석 데이터를 저장하여 테스트에 사용
 */
public class InMemoryAttendanceRepository implements AttendanceRepository {

    private final Map<String, Set<LocalDate>> store = new HashMap<>();

    @Override
    public boolean existsByDate(String username, LocalDate date) {
        return store.getOrDefault(username, Collections.emptySet())
                .contains(date);
    }

    @Override
    public void save(String username, LocalDate date) {
        store.computeIfAbsent(username, k -> new HashSet<>())
                .add(date);
    }

    // 통계용 메서드
    @Override
    public int countByUsername(String username) {
        return store.getOrDefault(username, Collections.emptySet()).size();
    }

    @Override
    public LocalDate findLastAttendanceDate(String username) {
        return store.getOrDefault(username, Collections.emptySet())
                .stream()
                .max(LocalDate::compareTo)
                .orElse(null);
    }

    @Override
    public List<LocalDate> findAllAttendanceDates(String username) {
        return store.getOrDefault(username, Collections.emptySet())
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
}
