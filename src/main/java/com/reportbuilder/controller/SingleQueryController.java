package com.reportbuilder.controller;

import com.reportbuilder.model.SingleQuery;
import com.reportbuilder.service.SingleQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/single-query", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SingleQueryController {

    private final SingleQueryService singleQueryService;

    @PostMapping(value = "/add-new-query", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody SingleQuery singleQuery) {
        singleQueryService.save(singleQuery);
    }

    @PutMapping(value = "/modify-single-query", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void update(@Valid @RequestBody SingleQuery singleQuery) {
        singleQueryService.update(singleQuery);
    }

    @DeleteMapping("/delete-single-query-by-id/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@PathVariable int id) {
        singleQueryService.deleteById(id);
    }

    @GetMapping("/execute-single-query-by-id/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void execute(@PathVariable int id) {
        singleQueryService.execute(id);
    }

    @GetMapping("/get-single-query-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SingleQuery getById(@PathVariable int id) {
        return singleQueryService.getById(id);
    }

    @GetMapping("/get-all-single-queries")
    @ResponseStatus(HttpStatus.OK)
    public List<SingleQuery> getAll() {
        return singleQueryService.getAll();
    }
}
