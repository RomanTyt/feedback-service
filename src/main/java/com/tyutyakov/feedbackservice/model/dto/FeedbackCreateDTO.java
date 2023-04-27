package com.tyutyakov.feedbackservice.model.dto;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FeedbackCreateDTO {

    private String feedbackAuthorName;

    private String orderId;

    private String feedbackText;

    private String advantagesOfTheProduct;

    private String disadvantagesOfTheProduct;

    private int deliverySpeedAssessment;

    private int productQualityAssessment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackCreateDTO that = (FeedbackCreateDTO) o;

        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }
}
