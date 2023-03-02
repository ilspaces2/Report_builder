package com.reportbuilder.service;

import com.reportbuilder.dto.ReportDto;
import com.reportbuilder.exception.ReportException;
import com.reportbuilder.model.ColumnReport;
import com.reportbuilder.model.Report;
import com.reportbuilder.model.TableReport;
import com.reportbuilder.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void save(Report report) {
        if (report.getTableAmount() != report.getTables().size()) {
            throw new ReportException("Table amount and tables size not equals");
        }
        List<String> tableNames = new ArrayList<>();
        for (TableReport tableReport : report.getTables()) {
            String tableName = tableReport.getTableName();
            equalsTableColumns(reportRepository.getTableColumns(tableName, false), tableReport.getColumns());
            tableNames.add(tableName);
        }
        reportRepository.save(new ReportDto(report.getReportId(), tableNames));
    }

    public Report getById(int id) {
        return reportRepository.getById(id).orElseThrow(() -> new ReportException("Report not found: " + id));
    }

    /**
     * Сравниваем два списка столбцов.
     *
     * @param baseColumns   список столбцов таблицы, полученный из базы данных.
     * @param reportColumns список столбцов таблицы, полученный из запроса post.
     */
    private void equalsTableColumns(List<ColumnReport> baseColumns, List<ColumnReport> reportColumns) {
        if (baseColumns.size() != reportColumns.size()) {
            throw new ReportException("Database columns and report columns are different");
        }
        for (int i = 0; i < baseColumns.size(); i++) {
            ColumnReport base = baseColumns.get(i);
            ColumnReport report = reportColumns.get(i);
            if (!base.getTitle().equalsIgnoreCase(report.getTitle())) {
                throw new ReportException("Column titles '" + report.getTitle() + "' not found");
            }
            if (!base.getType().equals(report.getType())) {
                throw new ReportException("Wrong column type: " + report.getTitle() + " - " + report.getType());
            }
        }
    }
}
