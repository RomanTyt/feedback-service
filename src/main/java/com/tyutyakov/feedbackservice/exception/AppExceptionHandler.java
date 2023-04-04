package com.tyutyakov.feedbackservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AppError> handleException(BusinessException e) {
        return new ResponseEntity<>(new AppError(e.getError(), e.getDescription()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppError> handleException1(Exception e) {
        return new ResponseEntity<>(new AppError("000", e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
