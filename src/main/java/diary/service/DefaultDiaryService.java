package diary.service;

import diary.model.Diary;
import diary.repository.DiaryRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * [DefaultDiaryService]
 * - DiaryService 인터페이스의 기본 구현체
 * - DiaryRepository를 통해 일기 저장, 조회, 삭제 기능을 처리
 */
public class DefaultDiaryService implements DiaryService {

    private final DiaryRepository diaryRepository;

    /**
     * 생성자
     * @param diaryRepository 일기 저장소 구현체 주입
     */
    public DefaultDiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    /**
     * 일기 저장
     */
    @Override
    public void saveDiary(Diary diary) {
        diaryRepository.save(diary);
    }

    /**
     * 사용자 전체 일기 조회
     */
    @Override
    public List<Diary> getAllDiaries(String username) {
        return diaryRepository.findAllByUsername(username);
    }

    /**
     * 특정 날짜의 일기 조회
     */
    @Override
    public Diary getDiaryByDate(String username, LocalDate date) {
        return diaryRepository.findByUsernameAndDate(username, date);
    }

    /**
     * 특정 날짜의 일기 삭제
     */
    @Override
    public void deleteDiary(String username, LocalDate date) {
        diaryRepository.deleteByUsernameAndDate(username, date);
    }
}