package com.tyutyakov.feedbackservice.exception.error.exception;

public abstract class BusinessException extends RuntimeException {
    public abstract String getErrorCode();
    public abstract String getDescription();
}
