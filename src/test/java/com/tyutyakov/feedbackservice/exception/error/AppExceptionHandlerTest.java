package com.tyutyakov.feedbackservice.exception.error;

import com.tyutyakov.feedbackservice.exception.error.exception.FeedbackExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;


class AppExceptionHandlerTest {

    private AppExceptionHandler sut;
    private FeedbackExistException feedbackExistException;

    @BeforeEach
    void setUp(){
        sut = new AppExceptionHandler();
        feedbackExistException = new FeedbackExistException();

    }

    @Test
    void handleException() {
        ResponseEntity<AppError> result = sut.handleException(new FeedbackExistException());
        assertEquals(feedbackExistException.getErrorCode(), result.getBody().getErrorCode());
    }

}