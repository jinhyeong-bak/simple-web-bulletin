package hello.simplewebbulletin.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment {
    private Long id;
    private Long pid;
    private String username;
    private String password;
    private String content;
    private LocalDateTime registerDate;

    public Comment() {
    }

    public Comment(Long id, Long pid, String username, String password, String content, LocalDateTime registerDate) {
        this.id = id;
        this.pid = pid;
        this.username = username;
        this.password = password;
        this.content = content;
        this.registerDate = registerDate;
    }
}
