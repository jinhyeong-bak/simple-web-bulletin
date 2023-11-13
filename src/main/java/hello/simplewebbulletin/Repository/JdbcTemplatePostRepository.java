package hello.simplewebbulletin.Repository;

import hello.simplewebbulletin.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;

@Repository("JdbcTemplatePostRepository")
public class JdbcTemplatePostRepository implements PostRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplatePostRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Post> findById(Long id) {
        String query = "SELECT * FROM post WHERE id = ?";

        List<Post> result = jdbcTemplate.query(query, postRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Post> findAll() {
        String query = "SELECT * FROM post";

        return jdbcTemplate.query(query, postRowMapper());
    }


    @Override
    public List<Post> findByTitle(String title) {
        String query = "SELECT * FROM post WHERE title LIKE ?";
        title = "%" + title + "%";

        return jdbcTemplate.query(query, postRowMapper(), title);
    }


    @Override
    public Post save(Post post) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("post").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", post.getTitle());
        parameters.put("content", post.getContent());
        parameters.put("register_date", post.getRegisterDate());
        parameters.put("username", post.getUserName());
        parameters.put("password", post.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        post.setId(key.longValue());
        return post;
    }

    @Override
    public Boolean remove(Long id) {
        String query = "DELETE FROM post WHERE id = ?";

        Optional<Post> toBeDeleted = findById(id);
        boolean isPresent = toBeDeleted.isPresent();

        if(isPresent == false) {
            return false;
        }

        int cnt = jdbcTemplate.update(query, id);

        if(cnt == 1) {
            return true;
        }
        return false;


    }

    @Override
    public void update(Post post) {
        Optional<Post> toBeUpdated = findById(post.getId());
        boolean isPresent = toBeUpdated.isPresent();

        if(isPresent == false) return;

       String query = "UPDATE post SET title = ?, content = ?, password = ? WHERE id = ?";
       jdbcTemplate.update(query, post.getTitle(), post.getContent(), post.getPassword(), post.getId());


    }


    private static RowMapper<Post> postRowMapper() {
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setUserName(rs.getString("username"));
            post.setPassword(rs.getString("password"));

            Timestamp timestamp = rs.getTimestamp("register_date");
            post.setRegisterDate(timestamp.toLocalDateTime());
            return post;
        };
    }


}
