package com.tyutyakov.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedback_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Feedback {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "feedback_id")
    private String feedbackID;
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;
    @Column(name = "feedback_author_name")
    private String feedbackAuthorName;
    @Column(name = "order_id ")
    private String orderID;
    @Column(name = "feedback_text")
    private String feedbackText;
    @Column(name = "advantages_of_the_product")
    private String advantagesOfTheProduct;
    @Column(name = "disadvantages_of_the_product")
    private String disadvantagesOfTheProduct;
    @Column(name = "delivery_speed_assessment")
    private int deliverySpeedAssessment;
    @Column(name = "product_quality_assessment")
    private int productQualityAssessment;
    @Column(name = "feedback_rating_like")
    private int feedbackRatingLike;
    @Column(name = "feedback_rating_dislike")
    private int feedbackRatingDislike;

}
