package com.tyutyakov.feedbackservice.exception.error;

import com.tyutyakov.feedbackservice.exception.error.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<AppError> handleException(BusinessException e) {
        return new ResponseEntity<>(new AppError(e.getErrorCode(), e.getDescription()), HttpStatus.BAD_REQUEST);
    }
}
