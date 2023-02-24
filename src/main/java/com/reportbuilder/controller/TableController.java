package com.reportbuilder.controller;

import com.reportbuilder.model.Table;
import com.reportbuilder.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/table", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TableController {

    private final TableRepository tableRepository;

    @PostMapping(value = "/create-table", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Table table) {
        tableRepository.save(table);
    }

    @GetMapping("get-table-by-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Table get(@PathVariable String name) {
        return tableRepository.getByName(name).get();
    }

    @DeleteMapping("drop-table/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void delete(@PathVariable String name) {
        tableRepository.deleteByName(name);
    }
}