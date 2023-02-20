package com.reportbuilder.repository;

import com.reportbuilder.model.Column;
import com.reportbuilder.model.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//https://mkyong.com/spring/spring-jdbctemplate-querying-examples/
@RequiredArgsConstructor
@Repository
public class TableRepositoryImpl implements TableRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Table table) {
        StringBuilder sb = new StringBuilder("CREATE TABLE ").append(table.getTableName()).append(" (");
        for (int i = 0; i < table.getColumnsAmount(); i++) {
            Column column = table.getColumnInfos().get(i);
            if (table.getPrimaryKey().equals(column.getTitle())) {
                sb.append(column.getTitle()).append(" ").append(column.getType());
                sb.append(" primary key");
            } else {
                sb.append(column.getTitle()).append(" ").append(column.getType());
            }
            if (i < table.getColumnsAmount() - 1) {
                sb.append(", ");
            }
        }
        sb.append(");");
        jdbcTemplate.execute(sb.toString());
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
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.execute(String.format("DROP TABLE %s;", name));
    }
}