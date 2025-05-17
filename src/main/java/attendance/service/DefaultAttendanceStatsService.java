package attendance.service;

import attendance.repository.AttendanceRepository;

import java.time.LocalDate;
import java.util.List;

public class DefaultAttendanceStatsService implements AttendanceStatsService {

    private final AttendanceRepository repository;

    public DefaultAttendanceStatsService(AttendanceRepository repository) {
        this.repository = repository;
    }

    @Override
    public int getTotalAttendanceCount(String username) {
        return repository.countByUsername(username);
    }

    @Override
    public LocalDate getLastAttendanceDate(String username) {
        return repository.findLastAttendanceDate(username);
    }

    @Override
    public List<LocalDate> getAllAttendanceDates(String username) {
        return repository.findAllAttendanceDates(username);
    }
}
