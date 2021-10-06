package com.company.common.exceptions;

import com.company.common.dtos.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @Getter
    private Map<String, ErrorCode> errorCodeMapper = new HashMap<>();

    public RestExceptionHandler() {
        // NOTE: The messages are fetched from exceptions thrown, such as:
        //
        // org.springframework.dao.DataIntegrityViolationException: could not execute statement; SQL [n/a]; constraint [fk1_tbl_product_groups];
        // nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement
        //
    }

    @ExceptionHandler(BackendException.class)
    public @ResponseBody
    ResponseEntity<Object> handleException(BackendException e) {
        ErrorCode errorCode = e.getErrorCode();
        return doHandleException(errorCode, e);
    }

    private ResponseEntity<Object> doHandleException(ErrorCode errorCode, Exception e) {
        logException(e);

        HttpStatus httpStatus = null;
        try {
            httpStatus = HttpStatus.valueOf(errorCode.getData().getHttpResponseCode());
        } catch (Exception exception) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            errorCode = ErrorCode.UNKNOWN_SERVER_ERROR;
        }

        ErrorCode.Data data = errorCode.getData();
        if (Objects.nonNull(e.getMessage())) {
            data.setDescription(String.format("%s : %s", data.getDescription(), e.getMessage()));
        }

        data.setLabel(errorCode.toString());

        ObjectMapper objectMapper = new ObjectMapper();
        String errorCodeJson = String.format("{ \"errorCode\": %s}", errorCode.getData().getCode());
        try {
            errorCodeJson = objectMapper.writeValueAsString(errorCode.getData());
        } catch (IOException i) {
            // pass
        }

        return ResponseEntity //
                .status(httpStatus)
                .body(errorCodeJson);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody
    ResponseEntity<Object> handleException(Exception e) {

        // Check if the throwable's message maps to any of our error codes
        //
        Optional<String> matchingException =
                getErrorCodeMapper().keySet().stream().filter(m -> e.getMessage().contains(m)).findAny();

        if (matchingException.isEmpty()) {
            // No match, and in this case, we want to see it!
            //
            log.error("matching exception is empty");
            return doHandleException(ErrorCode.UNKNOWN_SERVER_ERROR, e);
        } else {
            ErrorCode errorCode = getErrorCodeMapper().get(matchingException.get());
            return doHandleException(errorCode, e);
        }

    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleException(ConstraintViolationException ex) {

        log.error("ConstraintViolationException :", ex);

        Map<String, List<String>> errors = ex.getConstraintViolations().stream()
                .collect(groupingBy(
                        constraintViolation -> constraintViolation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                ));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private void logException(Exception e) {
        log.error("Handling exception ", e);
    }

}
