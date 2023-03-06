package com.reportbuilder.service;

import com.reportbuilder.exception.QueryException;
import com.reportbuilder.model.SingleQuery;
import com.reportbuilder.repository.SingleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SingleQueryService {

    private final SingleQueryRepository singleQueryRepository;

    public void save(SingleQuery singleQuery) {
        singleQueryRepository.save(singleQuery);
    }

    public void update(SingleQuery singleQuery) {
        getById(singleQuery.getQueryId());
        singleQueryRepository.update(singleQuery);
    }

    public void deleteById(int id) {
        getById(id);
        singleQueryRepository.deleteById(id);
    }

    public void execute(int id) {
        getById(id);
        singleQueryRepository.executeById(id);
    }

    public SingleQuery getById(int id) {
        return singleQueryRepository.getById(id).orElseThrow(() -> new QueryException("Query not found"));
    }

    public List<SingleQuery> getAll() {
        return singleQueryRepository.getAll();
    }
}
