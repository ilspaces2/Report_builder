package com.reportbuilder.repository.JdbcTemplate;

import com.reportbuilder.model.Column;
import com.reportbuilder.model.Table;
import com.reportbuilder.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TableRepositoryJdbcTemplateImpl implements TableRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public Optional<Table> getByName(String name) {
        try (Connection connection = (jdbcTemplate.getDataSource()).getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s;", name))) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            List<Column> columns = new ArrayList<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(new Column(
                        metaData.getColumnName(i).toUpperCase(),
                        metaData.getColumnTypeName(i).toUpperCase()));
            }
            return Optional.of(new Table(
                    name,
                    columns.size(),
                    columns,
                    columns.get(0).getTitle().toLowerCase()));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    // TODO when delete table then delete from report (table_names)
    @Override
    public void deleteByName(String name) {
        jdbcTemplate.execute(String.format("DROP TABLE %s;", name));
    }

    @Override
    public boolean tableExists(String name) {
        return jdbcTemplate.queryForObject(
                """
                        SELECT count(*) FROM information_schema.tables
                        WHERE table_name = ?
                        LIMIT 1;
                        """
                , Integer.class, name.toUpperCase()) == 1;
    }
}