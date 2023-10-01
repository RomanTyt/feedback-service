package com.tyutyakov.feedbackservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeedbackInfo {
    String orderId;
    String author;
    String text;
    int rating;
    int commentCount;
    LocalDate createDate;

    public FeedbackInfo(String orderId, String author, String text, int rating, int commentCount, LocalDate createDate) {
        this.orderId = orderId;
        this.author = author;
        this.text = text;
        this.rating = rating;
        this.commentCount = commentCount;
        this.createDate = createDate;
    }
}
