package com.tyutyakov.feedbackservice.service;

import com.tyutyakov.feedbackservice.model.dto.FeedbackRateDTO;
import com.tyutyakov.feedbackservice.model.entity.CommentToFeedback;
import com.tyutyakov.feedbackservice.model.entity.Feedback;
import com.tyutyakov.feedbackservice.model.entity.ResponseOrganization;
import com.tyutyakov.feedbackservice.repository.CommentToFeedbackRepository;
import com.tyutyakov.feedbackservice.repository.FeedbackRepository;
import com.tyutyakov.feedbackservice.repository.ResponseOrganizationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ResponseOrganizationRepository responseOrganizationRepository;
    private final CommentToFeedbackRepository commentToFeedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, ResponseOrganizationRepository responseOrganizationRepository, CommentToFeedbackRepository commentToFeedbackRepository) {
        this.feedbackRepository = feedbackRepository;
        this.responseOrganizationRepository = responseOrganizationRepository;
        this.commentToFeedbackRepository = commentToFeedbackRepository;
    }

    public Feedback findFeedbackById(String id){
        return feedbackRepository.findById(id).orElseThrow();
    }

    @Transactional
    public String createNewFeedback(Feedback feedback){
        feedbackRepository.save(feedback);
        return "ok";
    }

    @Transactional
    public String editFeedback(String id, Feedback updatedFeedback){
        updatedFeedback.setFeedbackID(id);
        feedbackRepository.save(updatedFeedback);
        return "ok";
    }

    @Transactional
    public String deleteFeedbackById(String id){
        feedbackRepository.deleteById(id);
        return "ok";
    }

    @Transactional
    public String addCommentToFeedback(String id, CommentToFeedback commentToFeedback){
        commentToFeedback.setFeedbackID(id);
        commentToFeedbackRepository.save(commentToFeedback);
        return "ok";
    }

    @Transactional
    public String rateFeedback(String id, FeedbackRateDTO feedbackRateDTO){
        boolean rate = feedbackRateDTO.getRate();
        Feedback temp = findFeedbackById(id);
        if (rate == true){
            temp.setFeedbackRatingLike(temp.getFeedbackRatingLike() + 1);
            feedbackRepository.save(temp);
        } else {
            temp.setFeedbackRatingDislike(temp.getFeedbackRatingDislike() + 1);
            feedbackRepository.save(temp);
        }
        return "Like - " + temp.getFeedbackRatingLike() + ", Dislike - " + temp.getFeedbackRatingDislike();
    }

    @Transactional
    public String addResponseOrganization(ResponseOrganization responseOrganization){
        responseOrganizationRepository.save(responseOrganization);
        return "ok";
    }
}
