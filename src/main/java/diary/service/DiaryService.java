package diary.service;

import diary.model.Diary;

import java.time.LocalDate;
import java.util.List;

/**
 * [DiaryService 인터페이스]
 * - 오늘 일기를 저장하거나, 사용자별 일기를 조회/삭제하는 비즈니스 로직 제공
 */
public interface DiaryService {

    /**
     * 일기를 저장한다.
     */
    void saveDiary(Diary diary);

    /**
     * 사용자별 전체 일기를 조회한다.
     */
    List<Diary> getAllDiaries(String username);

    /**
     * 특정 날짜의 일기를 조회한다.
     */
    Diary getDiaryByDate(String username, LocalDate date);

    /**
     * 특정 날짜의 일기를 삭제한다.
     */
    void deleteDiary(String username, LocalDate date);
}