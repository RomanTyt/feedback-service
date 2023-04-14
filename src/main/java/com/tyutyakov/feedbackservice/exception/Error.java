package com.tyutyakov.feedbackservice.exception;

import org.springframework.http.HttpStatus;

public enum Error {
    FEEDBACK_EXIST_EXCEPTION("1234", "Отзыв к этому заказу уже есть в БД", HttpStatus.BAD_REQUEST),
    FEEDBACK_NOT_FOUND_EXCEPTION("123", "Отзыв не найден в БД.", HttpStatus.BAD_REQUEST),
    ORGANIZATION_REPLY_EXIST_EXCEPTION("12345", "Ответ на этот отзыв уже есть в БД.", HttpStatus.BAD_REQUEST),
    ORGANIZATION_REPLY_NOT_FOUND_EXCEPTION("123456", "Ответ на этот отзыв не найден в БД", HttpStatus.BAD_REQUEST);

    private final String errorCode;
    private final String errorDescription;
    private final HttpStatus httpStatus;

    Error(String errorCode, String errorDescription, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
