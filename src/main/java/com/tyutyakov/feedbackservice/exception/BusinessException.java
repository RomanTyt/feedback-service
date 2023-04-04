package com.tyutyakov.feedbackservice.exception;

public abstract class BusinessException extends RuntimeException {
    public abstract String getError();
    public abstract String getDescription();
}
