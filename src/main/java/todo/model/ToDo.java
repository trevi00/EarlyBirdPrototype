package todo.model;

import java.time.LocalDate;

/**
 * [ToDo]
 * - 사용자의 하루 할 일을 나타내는 모델 클래스
 */
public class ToDo {

    private final String username;
    private final LocalDate date;
    private final String title;
    private final String content;
    private final boolean done;

    // 주요 생성자 (title + content 포함)
    public ToDo(String username, LocalDate date, String title, String content) {
        this.username = username;
        this.date = date;
        this.title = title;
        this.content = content;
        this.done = false; // 기본값
    }

    // 다른 생성자 (done 상태를 명시적으로 설정)
    public ToDo(String username, LocalDate date, String title, String content, boolean done) {
        this.username = username;
        this.date = date;
        this.title = title;
        this.content = content;
        this.done = done;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return done;
    }
}
