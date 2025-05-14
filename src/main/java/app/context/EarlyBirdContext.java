package app.context;

import attendance.repository.AttendanceRepository;
import attendance.repository.JdbcAttendanceRepository;
import attendance.service.AttendanceService;
import attendance.service.DefaultAttendanceService;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.point.PointManager;
import bird.service.BirdService;
import bird.service.DefaultBirdService;
import bird.service.StageEvolutionPolicy;
import config.DatabaseConfig;
import diary.repository.DiaryRepository;
import diary.repository.JdbcDiaryRepository;
import diary.service.DefaultDiaryService;
import diary.service.DiaryService;
import todo.repository.ToDoRepository;
import todo.repository.JdbcToDoRepository;
import todo.service.ToDoService;
import todo.service.DefaultToDoService;
import user.repository.JdbcUserRepository;
import user.repository.UserRepository;
import user.service.UserService;
import weather.cache.InMemoryWeatherCacheManager;
import weather.cache.WeatherCacheManager;
import weather.fetcher.OpenWeatherFetcher;
import weather.fetcher.WeatherFetcher;
import weather.service.DefaultWeatherService;
import weather.service.WeatherService;

import java.sql.Connection;

/**
 * [EarlyBirdContext]
 * - 시스템 전체의 의존성을 구성 및 주입하는 설정 클래스
 * - DB 연결, 서비스 초기화, 캐시 및 메시지 프로바이더 관리
 */
public class EarlyBirdContext {

    public final AttendanceService attendanceService;
    public final PointManager pointManager;
    public final Bird bird;
    public final BirdService birdService;
    public final BirdMessageProvider birdMessageProvider;
    public final WeatherService weatherService;
    public final DiaryService diaryService;
    public final UserService userService;
    public final ToDoService toDoService; // ✅ 할 일 서비스

    public EarlyBirdContext() {
        // 포인트 관리자
        pointManager = new PointManager();

        // DB 연결
        Connection conn = DatabaseConfig.getConnection();

        // 출석 시스템 구성
        AttendanceRepository attendanceRepo = new JdbcAttendanceRepository(conn);
        attendanceService = new DefaultAttendanceService(attendanceRepo, pointManager);

        // 새 시스템 구성
        bird = new Bird();
        birdService = new DefaultBirdService(new StageEvolutionPolicy());
        birdMessageProvider = new BirdMessageProvider();

        // 날씨 시스템 구성 (캐시 + 외부 API)
        WeatherCacheManager cacheManager = new InMemoryWeatherCacheManager();
        WeatherFetcher fetcher = new OpenWeatherFetcher();
        weatherService = new DefaultWeatherService(cacheManager, fetcher);

        // 일기 시스템 구성
        DiaryRepository diaryRepo = new JdbcDiaryRepository(conn);
        diaryService = new DefaultDiaryService(diaryRepo);

        // 사용자 시스템 구성
        UserRepository userRepo = new JdbcUserRepository(conn);
        userService = new UserService(userRepo);

        // 할 일 시스템 구성
        ToDoRepository toDoRepo = new JdbcToDoRepository(conn);
        toDoService = new DefaultToDoService(toDoRepo, pointManager);
    }
}
