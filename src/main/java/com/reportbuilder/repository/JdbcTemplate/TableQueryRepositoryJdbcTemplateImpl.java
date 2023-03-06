package com.reportbuilder.repository.JdbcTemplate;

import com.reportbuilder.model.TableQuery;
import com.reportbuilder.repository.TableQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TableQueryRepositoryJdbcTemplateImpl implements TableQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(TableQuery tableQuery) {
        jdbcTemplate.update(
                "insert into table_query (query_id, table_name, query) values (?,?,?)",
                tableQuery.getQueryId(),
                tableQuery.getTableName(),
                tableQuery.getQuery());
    }

    @Override
    public void update(TableQuery tableQuery) {
        jdbcTemplate.update(
                "update table_query set query=? where query_id=?",
                tableQuery.getQuery(),
                tableQuery.getQueryId());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("delete from table_query where query_id=?", id);
    }

    @Override
    public void executeById(int id) {
        String sql = jdbcTemplate.queryForObject("select query from table_query where query_id=?", String.class, id);
        if (sql != null) {
            jdbcTemplate.execute(sql);
        }
    }

    @Override
    public List<TableQuery> getAllByName(String name) {
        return jdbcTemplate.queryForList("select * from table_query where table_name=?", TableQuery.class, name);
    }

    @Override
    public Optional<TableQuery> getById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from table_query where query_id=?", TableQuery.class, id));
    }

    @Override
    public List<TableQuery> getAll() {
        return jdbcTemplate.queryForList("select * from table_query", TableQuery.class);
    }
}
