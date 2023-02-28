package com.reportbuilder.service;

import com.reportbuilder.dto.ReportDto;
import com.reportbuilder.model.ColumnReport;
import com.reportbuilder.model.Report;
import com.reportbuilder.model.TableReport;
import com.reportbuilder.model.TypeOfData;
import com.reportbuilder.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public void save(Report report) {
        if (report.getTableAmount() != report.getTables().size()) {
            throw new NoSuchElementException();
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
        return reportRepository.getById(id).orElse(null);
    }

    /**
     * Сравниваем два списка колонок.
     *
     * @param baseColumns   список колонок таблицы, полученный из базы данных.
     * @param reportColumns список колонок таблицы, полученный из запроса post.
     */
    private void equalsTableColumns(List<ColumnReport> baseColumns, List<ColumnReport> reportColumns) {
        if (baseColumns.size() != reportColumns.size()) {
            throw new NoSuchElementException("Base columns capacity not equal report column capacity");
        }
        for (int i = 0; i < baseColumns.size(); i++) {
            ColumnReport base = baseColumns.get(i);
            ColumnReport report = reportColumns.get(i);
            if (!base.getTitle().equalsIgnoreCase(report.getTitle())) {
                throw new NoSuchElementException("Column titles not equals");
            }
            if (!base.getType().equals(report.getType())) {
                throw new NoSuchElementException("Column types not equals");
            }
        }
    }
}
