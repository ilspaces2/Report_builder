package com.reportbuilder.handler;

import com.reportbuilder.exception.ReportException;
import com.reportbuilder.exception.TableException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpStatus NOT_ACCEPTABLE = HttpStatus.NOT_ACCEPTABLE;

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail body = ex.updateAndGetBody(this.messageSource, LocaleContextHolder.getLocale());
        Map<String, String> invalidParams = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            invalidParams.put(error.getField(), error.getDefaultMessage());
        }
        body.setProperty("invalid_params", invalidParams);
        body.setStatus(NOT_ACCEPTABLE);
        log.error("ArgumentNotValidException: {}", invalidParams);
        return handleExceptionInternal(ex, body, headers, NOT_ACCEPTABLE, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Json parse exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, NOT_ACCEPTABLE, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Path variable parse exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> pathVariableValidationExceptions(ConstraintViolationException ex, WebRequest request) {
        log.error("Path variable validation exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> dataAccessException(DataAccessException ex, WebRequest request) {
        log.error("SQL query error: {}", ex.getCause().getMessage());
        return createProblemDetailExceptionResponse(ex, true, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> sqlException(SQLException ex, WebRequest request) {
        log.error("SQL all errors: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(ReportException.class)
    public ResponseEntity<?> reportException(ReportException ex, WebRequest request) {
        log.error("Report exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, NOT_ACCEPTABLE, request);
    }

    @ExceptionHandler(TableException.class)
    public ResponseEntity<?> tableException(TableException ex, WebRequest request) {
        log.error("Table exception: {}", ex.getMessage());
        return createProblemDetailExceptionResponse(ex, false, NOT_ACCEPTABLE, request);
    }

    private ResponseEntity<Object> createProblemDetailExceptionResponse(Exception ex, boolean cause, HttpStatusCode statusCode, WebRequest request) {
        ProblemDetail body = createProblemDetail(ex, statusCode, cause ? ex.getCause().getMessage() : ex.getMessage(), null, null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), statusCode, request);
    }
}
