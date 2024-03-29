package com.tyutyakov.feedbackservice.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FeedbackGetDTO {

    private LocalDateTime dateTimeCreation;

    private LocalDateTime dateTimeUpdate;

    private String feedbackAuthorName;

    private String orderId;

    private String feedbackText;

    private String advantagesOfTheProduct;

    private String disadvantagesOfTheProduct;

    private int deliverySpeedAssessment;

    private int productQualityAssessment;

    private int feedbackRatingLike;

    private int feedbackRatingDislike;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackGetDTO that = (FeedbackGetDTO) o;

        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }
}
