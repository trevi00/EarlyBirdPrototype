package diary.service;

import diary.model.Diary;
import diary.repository.DiaryRepository;
import diary.repository.InMemoryDiaryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DiaryServiceTest {

    private DiaryService diaryService;
    private DiaryRepository diaryRepository;

    @BeforeEach
    void setUp() {
        diaryRepository = new InMemoryDiaryRepository();
        diaryService = new DefaultDiaryService(diaryRepository);
    }

    @Test
    void testSaveAndGetDiary() {
        LocalDate date = LocalDate.now();
        Diary diary = new Diary("user1", date, "맑음", "제목1", "내용1");

        diaryService.saveDiary(diary);
        Diary result = diaryService.getDiaryByDate("user1", date);

        assertNotNull(result);
        assertEquals("제목1", result.getTitle());
        assertEquals("내용1", result.getContent());
        assertEquals("맑음", result.getWeather());
    }

    @Test
    void testDeleteDiary() {
        LocalDate date = LocalDate.now();
        Diary diary = new Diary("user2", date, "흐림", "제목2", "내용2");

        diaryService.saveDiary(diary);
        diaryService.deleteDiary("user2", date);

        Diary deleted = diaryService.getDiaryByDate("user2", date);
        assertNull(deleted, "삭제된 일기는 조회되지 않아야 합니다.");
    }
}
