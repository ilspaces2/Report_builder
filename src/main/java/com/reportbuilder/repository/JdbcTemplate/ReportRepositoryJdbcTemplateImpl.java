package com.reportbuilder.repository.JdbcTemplate;

import com.reportbuilder.dto.ReportDto;
import com.reportbuilder.model.Column;
import com.reportbuilder.model.Report;
import com.reportbuilder.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryJdbcTemplateImpl implements ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(ReportDto reportDto) {
        jdbcTemplate.update(
                "insert into table_reports (reportId, tableNames, query) values (?,?)",
                reportDto.getReportId(),
                reportDto.getTableNames());
    }

    @Override
    public Optional<Report> getById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Column> getTableColumns(String tableName) {
        return jdbcTemplate.queryForList(
                """
                        SELECT column_name as title ,data_type as type FROM information_schema.tables
                        WHERE table_name = ?;
                        """,
                Column.class, tableName.toUpperCase());
    }
}
