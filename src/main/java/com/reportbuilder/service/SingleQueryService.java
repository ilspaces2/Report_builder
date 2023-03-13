package com.reportbuilder.service;

import com.reportbuilder.exception.QueryException;
import com.reportbuilder.model.SingleQuery;
import com.reportbuilder.repository.SingleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SingleQueryService {

    private final SingleQueryRepository singleQueryRepository;

    public void save(SingleQuery singleQuery) {
        singleQueryRepository.save(singleQuery);
    }

    public void update(SingleQuery singleQuery) {
        checkId(singleQuery.getQueryId());
        singleQueryRepository.update(singleQuery);
    }

    public void deleteById(int id) {
        checkId(id);
        singleQueryRepository.deleteById(id);
    }

    public void execute(int id) {
        checkId(id);
        singleQueryRepository.executeById(id);
    }

    public SingleQuery getById(int id) {
        Optional<SingleQuery> singleQuery = singleQueryRepository.getById(id);
        if (singleQuery.isEmpty()) {
            throw new NoSuchElementException("QueryId not found");
        }
        return singleQuery.get();
    }

    public List<SingleQuery> getAll() {
        return singleQueryRepository.getAll();
    }

    private void checkId(int id) {
        singleQueryRepository.getById(id).orElseThrow(() -> new QueryException("Query not found"));
    }

}


