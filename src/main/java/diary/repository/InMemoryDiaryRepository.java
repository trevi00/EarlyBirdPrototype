package diary.repository;

import diary.model.Diary;

import java.time.LocalDate;
import java.util.*;

public class InMemoryDiaryRepository implements DiaryRepository {

    private final Map<String, Map<LocalDate, Diary>> diaryData = new HashMap<>();

    @Override
    public void save(Diary diary) {
        diaryData.putIfAbsent(diary.getUsername(), new HashMap<>());
        diaryData.get(diary.getUsername()).put(diary.getDate(), diary);
    }

    @Override
    public List<Diary> findAllByUsername(String username) {
        if (!diaryData.containsKey(username)) return Collections.emptyList();
        return new ArrayList<>(diaryData.get(username).values());
    }

    @Override
    public Diary findByUsernameAndDate(String username, LocalDate date) {
        return diaryData.containsKey(username) ? diaryData.get(username).get(date) : null;
    }

    @Override
    public void deleteByUsernameAndDate(String username, LocalDate date) {
        if (diaryData.containsKey(username)) {
            diaryData.get(username).remove(date);
        }
    }
}