package com.reportbuilder.repository.JdbcTemplate;

import com.reportbuilder.model.SingleQuery;
import com.reportbuilder.repository.SingleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class SingleQueryRepositoryJdbcTemplateImpl implements SingleQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(SingleQuery singleQuery) {
        jdbcTemplate.update(
                "insert into single_query (query_id, query) values (?,?)",
                singleQuery.getQueryId(),
                singleQuery.getQuery());
    }

    @Override
    public void update(SingleQuery singleQuery) {
        jdbcTemplate.update(
                "update single_query set query=? where query_id=?",
                singleQuery.getQuery(),
                singleQuery.getQueryId());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("delete from single_query where query_id=?", id);
    }

    @Override
    public void executeById(int id) {
        String sql = jdbcTemplate.queryForObject("select query from single_query where query_id=?", String.class, id);
        if (sql != null) {
            jdbcTemplate.execute(sql);
        }
    }

    @Override
    public Optional<SingleQuery> getById(int id) {
        List<SingleQuery> singleQueries = jdbcTemplate.query("select * from single_query where query_id=?",
                new BeanPropertyRowMapper<>(SingleQuery.class),
                id);
        return singleQueries.isEmpty() ? Optional.empty() : Optional.of(singleQueries.get(0));
    }

    @Override
    public List<SingleQuery> getAll() {
        return jdbcTemplate.query("select * from single_query", new BeanPropertyRowMapper<>(SingleQuery.class));
    }
}
