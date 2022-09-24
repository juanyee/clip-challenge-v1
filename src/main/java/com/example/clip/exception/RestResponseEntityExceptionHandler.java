package com.example.clip.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.PersistenceException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author juan.yee
 */
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {PersistenceException.class})
    protected ResponseEntity<Object> handlePersistenceException(PersistenceException ex, WebRequest request) {
        final ClipError clipError = new ClipError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong when in database communication",
                ((ServletWebRequest)request).getRequest().getRequestURI(), ex.getMessage());
        return handleExceptionInternal(ex, clipError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {ClipNotFoundException.class})
    protected ResponseEntity<Object> handleClipNotFoundException(ClipNotFoundException ex, WebRequest request) {
        final ClipError clipError = new ClipError(LocalDateTime.now(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                "Not found, the requested item is not available",
                ((ServletWebRequest)request).getRequest().getRequestURI(), ex.getMessage());
        return handleExceptionInternal(ex, clipError, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        final ClipError clipError = new ClipError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An internal server error occurred, please contact with support team",
                ((ServletWebRequest)request).getRequest().getRequestURI(), ex.getMessage());
        log.error(Arrays.toString(ex.getStackTrace()));
        return handleExceptionInternal(ex, clipError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        StringJoiner errorMessage = new StringJoiner(", ");
        errors.forEach(errorMessage::add);

        final ClipError clipError = new ClipError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                "Bad request, invalid set of parameters were provided",
                ((ServletWebRequest)request).getRequest().getRequestURI(), errorMessage.toString());
        return handleExceptionInternal(ex, clipError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ClipError clipError = new ClipError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                "Bad request, invalid set of parameters were provided",
                ((ServletWebRequest)request).getRequest().getRequestURI(), ex.getMessage());
        return handleExceptionInternal(ex, clipError, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
