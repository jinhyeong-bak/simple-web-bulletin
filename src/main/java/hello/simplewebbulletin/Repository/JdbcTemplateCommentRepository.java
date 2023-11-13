package hello.simplewebbulletin.Repository;

import hello.simplewebbulletin.domain.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateCommentRepository implements CommentRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateCommentRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<Comment> commentRowMapper(){
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setPid(rs.getLong("pid"));
            comment.setContent(rs.getString("content"));
            comment.setUserName(rs.getString("username"));
            comment.setPassword(rs.getString("password"));

            Timestamp timestamp = rs.getTimestamp("register_date");
            comment.setRegisterDate(timestamp.toLocalDateTime());
            return comment;
        };
    }
    @Override
    public Optional<Comment> findById(Long id) {
        String query = "SELECT * FROM comment WHERE id = ?";

        return jdbcTemplate.query(query, commentRowMapper(), id).stream().findAny();
    }

    @Override
    public List<Comment> findByPostId(Long pid) {
        String query = "SELECT * FROM comment WHERE pid = ?";

        return jdbcTemplate.query(query, commentRowMapper(), pid);
    }

    @Override
    public List<Comment> findByUserName(String username) {
        String query = "SELECT * FROM comment WHERE username = ?";

        return jdbcTemplate.query(query, commentRowMapper(), username);
    }

    @Override
    public Comment save(Comment comment) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("comment").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("registerDate", LocalDateTime.now());
        parameters.put("pid", comment.getPid());
        parameters.put("content", comment.getContent());
        parameters.put("username", comment.getUserName());
        parameters.put("password", comment.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        comment.setId(key.longValue());
        return comment;
    }

    @Override
    public void update(Comment comment) {
        String query = "UPDATE comment SET username = ?, password = ?, content = ?";

        jdbcTemplate.update(query, comment.getUserName(), comment.getPassword(), comment.getContent());
    }
    @Override
    public Boolean remove(Comment comment) {
        String query = "DELETE FROM comment WHERE id = ?";

        Optional<Comment> found = findById(comment.getId());
        if(found.isEmpty()) return false;

        int cnt = jdbcTemplate.update(query, comment.getId());
        if(cnt == 1) return true;
        return false;
    }
}
