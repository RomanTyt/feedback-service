package com.tyutyakov.feedbackservice.exception;

public class OrganizationReplyExistException  extends BusinessException {
    private final String errorCode = "12345";
    private final String errorDescription = "Ответ на этот отзыв уже есть в БД.";

    @Override
    public String getError() {
        return errorCode;
    }

    @Override
    public String getDescription() {
        return errorDescription;
    }
}
