package kr.or.connect.todo.persistence;

import javax.sql.DataSource;

import kr.or.connect.todo.domain.Todo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TodoDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private final RowMapper<Todo> ROW_MAPPER = (rs, i) -> {
        Todo todo = new Todo();
        todo.setId(rs.getLong("id"));
        todo.setTodo(rs.getString("todo"));
        todo.setCompleted(rs.getBoolean("completed"));
        todo.setDate(rs.getTimestamp("date").toLocalDateTime());
        return todo;
    };

    public TodoDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("todo")
                .usingGeneratedKeyColumns("id");
    }

    public Long insert(Todo newTodo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(newTodo);
        return insertAction.executeAndReturnKey(params).longValue();
    }

    public Todo selectById(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, ROW_MAPPER);
    }

    public List<Todo> selectAll() {
        return jdbc.query(TodoSqls.SELECT_ALL, ROW_MAPPER);
    }
}
