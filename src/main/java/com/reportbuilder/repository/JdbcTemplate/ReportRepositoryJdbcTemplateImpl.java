package com.reportbuilder.repository.JdbcTemplate;

import com.reportbuilder.dto.ReportDto;
import com.reportbuilder.model.ColumnReport;
import com.reportbuilder.model.Report;
import com.reportbuilder.model.TableReport;
import com.reportbuilder.model.TypeOfData;
import com.reportbuilder.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryJdbcTemplateImpl implements ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void save(ReportDto reportDto) {
        jdbcTemplate.update(
                "insert into table_reports_id (report_id) values (?)",
                reportDto.getReportId());
        for (String tableName : reportDto.getTableNames()) {
            jdbcTemplate.update(
                    "insert into table_reports_names (report_id, tables_names) values (?,?)",
                    reportDto.getReportId(),
                    tableName);
        }
    }

    @Override
    public Optional<Report> getById(int id) {
        List<String> tableNames = jdbcTemplate.queryForList(
                "select tables_names from table_reports_names where report_id=?", String.class, id);
        if (tableNames.isEmpty()) {
            return Optional.empty();
        }
        List<TableReport> tableReports = new ArrayList<>();
        tableNames.forEach(name -> tableReports.add(new TableReport(name, getTableColumns(name,true))));
        return Optional.of(new Report(id, tableReports.size(), tableReports));
    }

    /**
     * Достаем, по имени таблицы, названия стобцов и их тип
     *
     * @param tableName имя таблицы.
     * @return список колонок.
     */
    @Override
    public List<ColumnReport> getTableColumns(String tableName,boolean withSize) {
        String sql = """
                select COLUMN_NAME title ,DATA_TYPE type from INFORMATION_SCHEMA.COLUMNS
                where TABLE_NAME = ?
                """;
        List<ColumnReport> reports = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ColumnReport.class), tableName.toUpperCase());
        if (reports.size() == 0) {
            throw new NoSuchElementException("Table not found");
        }
        return modificationColumns(reports, tableName,withSize);
    }

    private List<ColumnReport> modificationColumns(List<ColumnReport> reports, String tableName, boolean size) {
        return reports.stream().map(report -> {
            report.setTitle(report.getTitle().toLowerCase());
            if (size) {
                report.setSize(
                        jdbcTemplate.queryForObject(String.format("select count(%s) from %s", report.getTitle(), tableName),
                                Integer.class)
                );
            }
            if (report.getType().equals(TypeOfData.INTEGER.toString())) {
                report.setType(TypeOfData.int4.toString());
            }
            if (report.getType().equals(TypeOfData.getCharacterVarying())) {
                report.setType(TypeOfData.varchar.toString());
            }
            return report;
        }).toList();
    }
}
