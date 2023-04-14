package com.tyutyakov.feedbackservice.exception;

public class BusinessException extends RuntimeException{
    private final Error error;

    public BusinessException(Error error){
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
