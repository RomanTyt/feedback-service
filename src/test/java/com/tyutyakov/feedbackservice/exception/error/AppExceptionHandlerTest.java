package com.tyutyakov.feedbackservice.exception.error;

import com.tyutyakov.feedbackservice.exception.AppError;
import com.tyutyakov.feedbackservice.exception.AppExceptionHandler;
import com.tyutyakov.feedbackservice.exception.BusinessException;
import com.tyutyakov.feedbackservice.exception.Error;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppExceptionHandlerTest {
    private AppExceptionHandler sut;

    @BeforeEach
    void setUp(){
        sut = new AppExceptionHandler();
    }

    @Test
    void handleException() {
        ResponseEntity<AppError> result = sut.handleException(new BusinessException(Error.ORGANIZATION_REPLY_NOT_FOUND_EXCEPTION));
        assertEquals(Error.ORGANIZATION_REPLY_NOT_FOUND_EXCEPTION.getErrorCode(), result.getBody().getErrorCode());
    }

}