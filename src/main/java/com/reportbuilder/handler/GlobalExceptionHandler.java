package com.reportbuilder.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> dataAccessException(DataAccessException ex, WebRequest request) {
        log.error("SQL query error: {}", ex.getCause().getMessage());
        return createProblemDetailExceptionResponse(ex, true, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> sqlException(SQLException ex, WebRequest request) {
        log.error("SQL all errors: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementException(NoSuchElementException ex, WebRequest request) {
        log.error("No Such Element Exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, HttpStatus.NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerException(NullPointerException ex, WebRequest request) {
        log.error("Null Pointer Exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, HttpStatus.NOT_ACCEPTABLE, request);
    }

    private ResponseEntity<?> createProblemDetailExceptionResponse(Exception ex, boolean cause, HttpStatusCode statusCode, WebRequest request) {
        ProblemDetail body = createProblemDetail(ex, statusCode, cause ? ex.getCause().getMessage() : ex.getMessage(), null, null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), statusCode, request);
    }
}
