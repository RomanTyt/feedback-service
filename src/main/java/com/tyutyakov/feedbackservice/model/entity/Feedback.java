package com.tyutyakov.feedbackservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "feedback_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "feedback_id")
    private String feedbackId;

    @CreationTimestamp
    @Column(name = "date_time_creation")
    private LocalDateTime dateTimeCreation;

    @UpdateTimestamp
    @Column(name = "date_time_update")
    private LocalDateTime dateTimeUpdate;

    @Column(name = "feedback_author_name")
    private String feedbackAuthorName;

    @Column(name = "order_id ")
    private String orderId;

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


    @OneToMany(mappedBy = "feedback")
    @Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.PERSIST})
    private List<CommentToFeedback> comments;

    @OneToOne(mappedBy = "feedback")
    @Cascade({org.hibernate.annotations.CascadeType.REMOVE, org.hibernate.annotations.CascadeType.PERSIST})
    private OrganizationReply organizationReply;

    public void rateFeedback(boolean rate){
        if(rate){
            feedbackRatingLike += 1;
        }
        else {
            feedbackRatingDislike += 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Feedback feedback = (Feedback) o;
        return (feedbackId != null) && (Objects.equals(feedbackId, feedback.feedbackId));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
