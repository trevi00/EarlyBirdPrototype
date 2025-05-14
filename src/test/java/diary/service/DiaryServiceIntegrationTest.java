package diary.service;

import config.DatabaseConfig;
import diary.model.Diary;
import diary.repository.JdbcDiaryRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServiceIntegrationTest {

    static DiaryService diaryService;
    static Connection conn;

    @BeforeAll
    static void setup() {
        conn = DatabaseConfig.getConnection();
        diaryService = new DefaultDiaryService(new JdbcDiaryRepository(conn));
    }

    @Test
    void testSaveAndGetDiary() {
        LocalDate date = LocalDate.of(2025, 5, 12);
        Diary diary = new Diary("integration1", date, "맑음", "통합 테스트", "DB 저장 확인");

        diaryService.saveDiary(diary);
        Diary result = diaryService.getDiaryByDate("integration1", date);

        assertNotNull(result, "일기 저장 후 조회가 가능해야 합니다.");
        assertEquals("통합 테스트", result.getTitle());
    }

    @Test
    void testDeleteDiary() {
        LocalDate date = LocalDate.of(2025, 5, 13);
        Diary diary = new Diary("integration1", date, "흐림", "삭제 테스트", "삭제 확인용");

        diaryService.saveDiary(diary);
        diaryService.deleteDiary("integration1", date);

        Diary deleted = diaryService.getDiaryByDate("integration1", date);
        assertNull(deleted, "삭제된 일기는 조회 시 null 이어야 합니다.");
    }

    @Test
    void testGetAllDiaries() {
        // 여러 개 저장
        diaryService.saveDiary(new Diary("integration1", LocalDate.of(2025, 5, 14), "맑음", "A", "내용A"));
        diaryService.saveDiary(new Diary("integration1", LocalDate.of(2025, 5, 15), "비", "B", "내용B"));

        List<Diary> diaries = diaryService.getAllDiaries("integration1");

        assertNotNull(diaries);
        assertTrue(diaries.size() >= 2, "2개 이상의 일기를 가져와야 함");
    }
}
