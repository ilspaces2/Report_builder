package com.reportbuilder.repository.JdbcTemplate;

import com.reportbuilder.model.SingleQuery;
import com.reportbuilder.repository.SingleQueryRepository;
import lombok.RequiredArgsConstructor;
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
                "insert into single_query (queryId, query) values (?,?)",
                singleQuery.getQueryId(),
                singleQuery.getQuery());
    }

    @Override
    public void update(SingleQuery singleQuery) {
        jdbcTemplate.update(
                "update single_query set query=? where queryId=?",
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
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from single_query where queryId=?", SingleQuery.class, id));
    }

    @Override
    public List<SingleQuery> getAll() {
        return jdbcTemplate.queryForList("select * from single_query", SingleQuery.class);
    }
}
