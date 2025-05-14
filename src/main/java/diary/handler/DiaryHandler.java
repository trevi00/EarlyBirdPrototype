package diary.handler;

import diary.model.Diary;
import diary.service.DiaryService;
import user.session.SessionManager;
import weather.model.Weather;
import weather.service.WeatherService;

import java.time.LocalDate;
import java.util.List;

/**
 * [DiaryHandler]
 * - UI 계층에서 발생한 일기 요청을 처리하는 중간 계층
 * - DiaryService를 호출하여 일기 저장/조회/삭제 기능 수행
 * - SessionManager를 통해 현재 로그인한 사용자 정보를 참조
 * - WeatherService 연동으로 날씨 자동 기록 가능
 */
public class DiaryHandler {

    private final DiaryService diaryService;
    private final WeatherService weatherService;

    /**
     * 생성자
     * @param diaryService 일기 서비스
     * @param weatherService 날씨 서비스 (자동 기록용)
     */
    public DiaryHandler(DiaryService diaryService, WeatherService weatherService) {
        this.diaryService = diaryService;
        this.weatherService = weatherService;
    }

    /**
     * 현재 로그인한 사용자의 전체 일기 목록 조회
     */
    public List<Diary> getDiaryList() {
        String username = SessionManager.getCurrentUser().getUsername();
        return diaryService.getAllDiaries(username);
    }

    /**
     * 특정 날짜의 일기 조회
     */
    public Diary getDiaryByDate(LocalDate date) {
        String username = SessionManager.getCurrentUser().getUsername();
        return diaryService.getDiaryByDate(username, date);
    }

    /**
     * 특정 날짜의 일기 삭제
     */
    public void deleteDiary(LocalDate date) {
        String username = SessionManager.getCurrentUser().getUsername();
        diaryService.deleteDiary(username, date);
    }

    /**
     * 일기 저장 (날씨 수동 입력)
     */
    public void saveDiary(LocalDate date, String weather, String title, String content) {
        String username = SessionManager.getCurrentUser().getUsername();
        Diary diary = new Diary(username, date, weather, title, content);
        diaryService.saveDiary(diary);
    }

    /**
     * 일기 저장 (날씨 자동 조회 - 도시 고정: Seoul)
     */
    public void saveDiaryAutoWeather(LocalDate date, String title, String content) {
        String username = SessionManager.getCurrentUser().getUsername();

        String weather = "알 수 없음";
        Weather w = weatherService.getTodayWeather("Seoul");
        if (w != null) {
            weather = w.getDescription();
        }

        Diary diary = new Diary(username, date, weather, title, content);
        diaryService.saveDiary(diary);
    }
}