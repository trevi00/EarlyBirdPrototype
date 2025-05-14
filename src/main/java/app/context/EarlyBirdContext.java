package app.context;

import attendance.repository.AttendanceRepository;
import attendance.repository.JdbcAttendanceRepository;
import attendance.service.AttendanceService;
import attendance.service.DefaultAttendanceService;
import bird.message.BirdMessageProvider;
import bird.model.Bird;
import bird.repository.BirdRepository;
import bird.repository.JdbcBirdRepository;
import bird.repository.PointManager;
import bird.repository.PointRepository;
import bird.repository.JdbcPointRepository;
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
import user.session.SessionManager;
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
    public final ToDoService toDoService;

    public EarlyBirdContext() {
        Connection conn = DatabaseConfig.getConnection();

        // ✅ 사용자 ID (로그인 이후)
        String userId = SessionManager.getCurrentUser().getUsername();

        // ✅ 포인트 시스템
        PointRepository pointRepo = new JdbcPointRepository(conn);
        pointManager = new PointManager(pointRepo);

        // ✅ 출석 시스템
        AttendanceRepository attendanceRepo = new JdbcAttendanceRepository(conn);
        attendanceService = new DefaultAttendanceService(attendanceRepo, pointManager);

        // ✅ 새 시스템
        BirdRepository birdRepo = new JdbcBirdRepository(conn);
        birdService = new DefaultBirdService(new StageEvolutionPolicy(), birdRepo);
        bird = birdService.loadBird(userId); // 🔥 DB에서 사용자별 새 상태 로드
        birdMessageProvider = new BirdMessageProvider();

        // ✅ 날씨 시스템
        WeatherCacheManager cacheManager = new InMemoryWeatherCacheManager();
        WeatherFetcher fetcher = new OpenWeatherFetcher();
        weatherService = new DefaultWeatherService(cacheManager, fetcher);

        // ✅ 일기 시스템
        DiaryRepository diaryRepo = new JdbcDiaryRepository(conn);
        diaryService = new DefaultDiaryService(diaryRepo);

        // ✅ 사용자 시스템
        UserRepository userRepo = new JdbcUserRepository(conn);
        userService = new UserService(userRepo);

        // ✅ 할 일 시스템
        ToDoRepository toDoRepo = new JdbcToDoRepository(conn);
        toDoService = new DefaultToDoService(toDoRepo, pointManager);
    }
}
