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
        if (findTableByName(tableQuery.getTableName()) == 1) {
            jdbcTemplate.update(
                    "insert into table_query (queryId, tableName, query) values (?,?,?)",
                    tableQuery.getQueryId(),
                    tableQuery.getTableName(),
                    tableQuery.getQuery());
        }
    }

    @Override
    public void update(TableQuery tableQuery) {
        if (findTableByName(tableQuery.getTableName()) == 1) {
            jdbcTemplate.update(
                    "update table_query set query=? where queryId=?",
                    tableQuery.getQuery(),
                    tableQuery.getQueryId());
        }
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("delete from table_query where queryId=?", id);
    }

    @Override
    public void executeById(int id) {
        String sql = jdbcTemplate.queryForObject("select query from table_query where queryId=?", String.class, id);
        if (sql != null) {
            jdbcTemplate.execute(sql);
        }
    }

    @Override
    public List<TableQuery> getAllByName(String name) {
        return jdbcTemplate.queryForList("select * from table_query where tableName=?", TableQuery.class, name);
    }

    @Override
    public Optional<TableQuery> getById(int id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("select * from table_query where queryId=?", TableQuery.class, id));
    }

    @Override
    public List<TableQuery> getAll() {
        return jdbcTemplate.queryForList("select * from table_query", TableQuery.class);
    }

    private Integer findTableByName(String name) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT count(*) FROM information_schema.tables
                        WHERE table_name = ?
                        LIMIT 1;
                        """
                , Integer.class, name.toUpperCase());
    }
}
