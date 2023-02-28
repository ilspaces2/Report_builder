package com.reportbuilder.controller;

import com.reportbuilder.model.Report;
import com.reportbuilder.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping(value = "/create-report", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Report report) {
        reportService.save(report);
    }

    @GetMapping("/get-report-by-id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Report getById(@PathVariable int id) {
        return reportService.getById(id);
    }
}
