package com.tyutyakov.feedbackservice.exception.error.exception;

public class FeedbackNotFoundException extends BusinessException{
   private String errorCode = "123";
   private String errorDescription = "Отзыв не найден в БД.";

    public FeedbackNotFoundException() {
    }

    public FeedbackNotFoundException(String errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    @Override
    public String getError() {
        return errorCode;
    }

    @Override
    public String getDescription() {
        return errorDescription;
    }
}
