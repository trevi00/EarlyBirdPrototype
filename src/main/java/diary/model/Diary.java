package diary.model;

import java.time.LocalDate;

/**
 * [Diary 데이터 모델]
 * - 날짜, 제목, 내용, 날씨 정보를 가진다.
 */
public class Diary {

    private LocalDate date;
    private String weather;
    private String title;
    private String content;
    private String username;

    public Diary(String username, LocalDate date, String weather, String title, String content) {
        this.username = username;
        this.date = date;
        this.weather = weather;
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}