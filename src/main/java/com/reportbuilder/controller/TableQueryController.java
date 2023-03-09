package com.reportbuilder.controller;

import com.reportbuilder.model.TableQuery;
import com.reportbuilder.service.TableQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/table-query", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TableQueryController {

    private final TableQueryService tableQueryService;

    @PostMapping(value = "/add-new-query-to-table", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody TableQuery tableQuery) {
        tableQueryService.save(tableQuery);
    }

    @PutMapping(value = "/modify-query-in-table", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody TableQuery tableQuery) {
        tableQueryService.update(tableQuery);
    }

    @DeleteMapping("/delete-table-query-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        tableQueryService.deleteById(id);
    }

    @GetMapping("/execute-table-query-by-id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(@PathVariable int id) {
        tableQueryService.execute(id);
    }

    @GetMapping("/get-all-queries-by-table-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<TableQuery> getAllByTableName(@PathVariable String name) {
        return tableQueryService.getAllByName(name);
    }

    @GetMapping("/get-table-query-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TableQuery getById(@PathVariable int id) {
        return tableQueryService.getById(id);
    }

    @GetMapping("/get-all-table-queries")
    @ResponseStatus(HttpStatus.OK)
    public List<TableQuery> getAll() {
        return tableQueryService.getAll();
    }
}
