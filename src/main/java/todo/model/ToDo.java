package todo.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * [ToDo]
 * - 할 일 정보 모델
 */
public class ToDo {

    private final String id;              // 고유 식별자 (ex. UUID 해시 등)
    private final String username;        // 사용자명
    private final LocalDate date;         // 날짜
    private final String title;           // 제목
    private final String content;         // 내용
    private final boolean done;           // 완료 여부

    // 전체 필드 생성자 (DB 조회 시 사용)
    public ToDo(String id, String username, LocalDate date, String title, String content, boolean done) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.title = title;
        this.content = content;
        this.done = done;
    }

    // 신규 등록용 생성자 (id 자동 생성)
    public ToDo(String username, LocalDate date, String title, String content, boolean done) {
        this.id = generateId(username, date); // 내부 해시 기반 ID 생성
        this.username = username;
        this.date = date;
        this.title = title;
        this.content = content;
        this.done = done;
    }

    private String generateId(String username, LocalDate date) {
        return username + "-" + date.toString();
    }

    // getter
    public String getId() {
        return id;
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

    // 완료 상태 변경용 새 객체 생성
    public ToDo markAsDone() {
        return new ToDo(id, username, date, title, content, true);
    }

    // equals/hashCode → ID 기준
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDo)) return false;
        ToDo toDo = (ToDo) o;
        return Objects.equals(id, toDo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
