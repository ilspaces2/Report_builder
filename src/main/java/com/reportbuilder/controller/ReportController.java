package com.reportbuilder.controller;

import com.reportbuilder.model.Report;
import com.reportbuilder.service.ReportService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class ReportController {

    private final ReportService reportService;

    @PostMapping(value = "/create-report", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody Report report) {
        reportService.save(report);
    }

    @GetMapping("/get-report-by-id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Report getById(@PathVariable @Min(1) int id) {
        return reportService.getById(id);
    }
}
