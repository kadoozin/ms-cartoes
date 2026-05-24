package com.kadoozin.mscartoes.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso nao encontrado: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Recurso nao encontrado");
        problemDetail.setDetail(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("Erro de validacao no corpo da requisicao: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Erro de validacao");
        problemDetail.setDetail("Um ou mais campos estao invalidos.");
        problemDetail.setProperty("erros", extractFieldErrors(ex.getFieldErrors()));
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler({ConstraintViolationException.class, HandlerMethodValidationException.class})
    public ResponseEntity<ProblemDetail> handleConstraintViolation(Exception ex) {
        log.warn("Erro de validacao em parametros da requisicao: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Erro de validacao");
        problemDetail.setDetail("Um ou mais parametros estao invalidos.");
        if (ex instanceof ConstraintViolationException constraintViolationException) {
            problemDetail.setProperty("erros", extractConstraintViolations(constraintViolationException));
        }
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("JSON malformado no corpo da requisicao: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("JSON malformado");
        problemDetail.setDetail("JSON malformado no corpo da requisicao. Verifique aspas, virgulas e chaves.");
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Erro de regra de negocio: {}", ex.getMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Regra de negocio invalida");
        problemDetail.setDetail(ex.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    private List<Map<String, Object>> extractFieldErrors(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> {
                    Map<String, Object> error = new LinkedHashMap<>();
                    error.put("campo", fieldError.getField());
                    error.put("mensagem", fieldError.getDefaultMessage());
                    error.put("valorRejeitado", fieldError.getRejectedValue());
                    return error;
                })
                .toList();
    }

    private List<Map<String, Object>> extractConstraintViolations(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(violation -> {
                    Map<String, Object> error = new LinkedHashMap<>();
                    error.put("campo", violation.getPropertyPath().toString());
                    error.put("mensagem", violation.getMessage());
                    error.put("valorRejeitado", violation.getInvalidValue());
                    return error;
                })
                .toList();
    }
}
