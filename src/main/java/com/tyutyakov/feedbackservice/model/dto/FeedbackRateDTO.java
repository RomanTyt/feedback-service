package com.tyutyakov.feedbackservice.model.dto;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class FeedbackRateDTO {
    private boolean rate;


    public boolean getRate() {
        return rate;
    }
    public void setRate(boolean rate) {
        this.rate = rate;
    }

    public FeedbackRateDTO(boolean rate) {
        this.rate = rate;
    }
    public FeedbackRateDTO(){}
}
