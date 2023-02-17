package com.reportbuilder.repository;

import com.reportbuilder.model.Report;

import java.util.Optional;

public interface ReportRepository {

    void save(Report report);

    Optional<Report> getById(int id);
}
