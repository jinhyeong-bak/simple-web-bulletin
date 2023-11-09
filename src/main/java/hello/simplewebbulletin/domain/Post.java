package hello.simplewebbulletin.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
public class Post {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime registerDate;
    private String userName;
    private String password;

    public Post() {
    }

    public Post(Long id, String title, String content, LocalDateTime registerDate, String userName, String password) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.registerDate = registerDate;
        this.userName = userName;
        this.password = password;
    }

    public Post(String title, String content, LocalDateTime registerDate, String userName, String password) {
        this.title = title;
        this.content = content;
        this.registerDate = registerDate;
        this.userName = userName;
        this.password = password;
    }
}