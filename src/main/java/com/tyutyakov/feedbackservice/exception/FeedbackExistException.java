package com.tyutyakov.feedbackservice.exception;

public class FeedbackExistException extends BusinessException {
    private final String errorCode = "1234";
    private final String errorDescription = "Отзыв к этому заказу уже есть в БД";

    @Override
    public String getError() {
        return errorCode;
    }

    @Override
    public String getDescription() {
        return errorDescription;
    }
}
