package com.tyutyakov.feedbackservice.service;

import com.tyutyakov.feedbackservice.exception.BusinessException;
import com.tyutyakov.feedbackservice.exception.Error;
import com.tyutyakov.feedbackservice.model.dto.*;
import com.tyutyakov.feedbackservice.model.entity.CommentToFeedback;
import com.tyutyakov.feedbackservice.model.entity.Feedback;
import com.tyutyakov.feedbackservice.model.entity.OrganizationReply;
import com.tyutyakov.feedbackservice.model.mapper.CommentToFeedbackMapper;
import com.tyutyakov.feedbackservice.model.mapper.FeedbackMapper;
import com.tyutyakov.feedbackservice.model.mapper.OrganizationReplyMapper;
import com.tyutyakov.feedbackservice.repository.CommentToFeedbackRepository;
import com.tyutyakov.feedbackservice.repository.FeedbackRepository;
import com.tyutyakov.feedbackservice.repository.OrganizationReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final OrganizationReplyRepository organizationReplyRepository;
    private final CommentToFeedbackRepository commentToFeedbackRepository;
    private final FeedbackMapper feedbackMapper;
    private final OrganizationReplyMapper organizationReplyMapper;
    private final CommentToFeedbackMapper commentToFeedbackMapper;

    public FeedbackService(FeedbackRepository feedbackRepository, OrganizationReplyRepository organizationReplyRepository,
                           CommentToFeedbackRepository commentToFeedbackRepository, FeedbackMapper feedbackMapper,
                           OrganizationReplyMapper organizationReplyMapper, CommentToFeedbackMapper commentToFeedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.organizationReplyRepository = organizationReplyRepository;
        this.commentToFeedbackRepository = commentToFeedbackRepository;
        this.feedbackMapper = feedbackMapper;
        this.organizationReplyMapper = organizationReplyMapper;
        this.commentToFeedbackMapper = commentToFeedbackMapper;
    }

    public List<FeedbackInfo> getAllFeedbacks(){
        List<Feedback> feedbackList = feedbackRepository.findAll();
        List<FeedbackInfo> feedbackInfoList = new ArrayList<>();
        for (Feedback feedback: feedbackList) {
            feedbackInfoList.add(feedbackMapper.FeedbackMapToFeedbackInfo(feedback));
        }
        return feedbackInfoList;
    }

    /**
     * Проверка наличия отзыва в БД
     *
     * @param feedbackId  id отзыва
     * @return отзыв(entity)
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    public Feedback getFeedbackById(String feedbackId){
        return feedbackRepository.findById(feedbackId).orElseThrow(() -> new BusinessException(Error.FEEDBACK_NOT_FOUND_EXCEPTION, feedbackId));
    }

    /**
     * Найти отзыв по id заказа
     *
     * @param orderId  id заказа
     * @return  отзыв(entity)
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    public Feedback findFeedbackByOrderId(String orderId){
        return feedbackRepository.findFeedbackByOrderId(orderId).orElseThrow(() -> new BusinessException(Error.FEEDBACK_NOT_FOUND_EXCEPTION, "к заказу " + orderId));
    }

    /**
     * Найти отзыв по id
     *
     * @param feedbackId  id отзыва
     * @return  отзыв(DTO)
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    public FeedbackGetDTO findFeedbackById(String feedbackId){
        return feedbackMapper.feedbackMapToFeedbackGetDTO(getFeedbackById(feedbackId));
    }

    /**
     * Создать новый отзыв
     *
     * @param feedbackCreateDTO  отзыв(DTO)
     * @return id созданного отзыва
     * @exception BusinessException FEEDBACK_EXIST_EXCEPTION(если ответ на этот отзыв уже есть в БД)
     */
    @Transactional
    public String createNewFeedback(FeedbackCreateDTO feedbackCreateDTO){
        Optional<Feedback> feedbackByOrderId = feedbackRepository.findFeedbackByOrderId(feedbackCreateDTO.getOrderId());
        if (feedbackByOrderId.isPresent()){
            throw new BusinessException(Error.FEEDBACK_EXIST_EXCEPTION, feedbackByOrderId.get().getFeedbackId());
        }
        Feedback feedback = feedbackMapper.feedbackCreateDTOMapToFeedback(feedbackCreateDTO);
        return feedbackRepository.save(feedback).getFeedbackId();
    }

    /**
     * Редактировать отзыв
     *
     * @param feedbackId  id отзыва
     * @param feedbackUpdateDTO  отзыв(DTO)
     * @return  отзыв(DTO)
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    @Transactional
    public FeedbackGetDTO editFeedback(String feedbackId, FeedbackUpdateDTO feedbackUpdateDTO){
        Feedback updatedFeedback = getFeedbackById(feedbackId);
        feedbackMapper.update(updatedFeedback, feedbackUpdateDTO);
        feedbackRepository.save(updatedFeedback);
        return feedbackMapper.feedbackMapToFeedbackGetDTO(getFeedbackById(feedbackId));
    }

    /**
     * Удалить отзыв и связанные с ним данные(ответ организации и комментарии)
     *
     * @param feedbackId  id отзыва
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    @Transactional
    public void deleteFeedbackById(String feedbackId){
        getFeedbackById(feedbackId);
        feedbackRepository.deleteById(feedbackId);
    }

    /**
     * Добавить комментарий к отзыву от другого пользователя
     *
     * @param feedbackId  id отзыва
     * @param commentToFeedbackCreateDTO  комментарий(DTO)
     * @return  id комментария
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    @Transactional
    public String addCommentToFeedback(String feedbackId, CommentToFeedbackCreateDTO commentToFeedbackCreateDTO){
        Feedback feedback = getFeedbackById(feedbackId);
        CommentToFeedback commentToFeedback = commentToFeedbackMapper.createDtoMapToCommentToFeedback(commentToFeedbackCreateDTO);
        commentToFeedback.setFeedback(feedback);
        return commentToFeedbackRepository.save(commentToFeedback).getCommentId();
    }

    /**
     * Получить все комментарии к отзыву
     *
     * @param feedbackId  id отзыва
     * @return  List комментариев
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    public List<CommentToFeedbackGetDTO> getAllCommentToFeedback(String feedbackId){
        Feedback feedback = getFeedbackById(feedbackId);
        return commentToFeedbackMapper.convertListCommentToFeedbackToListCommentToFeedbackGetDTO(feedback.getComments());
    }

    /**
     * Оценить отзыв(лайк/дизлайк)
     *
     * @param feedbackId  id отзыва
     * @param feedbackRateDTO  оценка(лайк/дизлайк)(DTO)
     * @return количество лайков и дизлайков этого отзыва
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    @Transactional
    public String rateFeedback(String feedbackId, FeedbackRateDTO feedbackRateDTO){
        Feedback feedback = getFeedbackById(feedbackId);
        feedback.rateFeedback(feedbackRateDTO.getRate());
        feedbackRepository.save(feedback);
        return "Like - " + feedback.getFeedbackRatingLike() + ", Dislike - " + feedback.getFeedbackRatingDislike();
    }

    /**
     * Добавить ответ на отзыв от организации
     *
     * @param feedbackId  id отзыва
     * @param organizationReplyCreateDTO  ответ на отзыв от организации(DTO)
     * @return  id созданного ответа
     * @exception BusinessException ORGANIZATION_REPLY_EXIST_EXCEPTION (если ответ на отзыв уже есть в БД)
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    @Transactional
    public String addOrganizationReply(String feedbackId, OrganizationReplyCreateDTO organizationReplyCreateDTO){
        if (organizationReplyRepository.existsOrganizationReplyByFeedback_feedbackId(feedbackId)){
            throw new BusinessException(Error.ORGANIZATION_REPLY_EXIST_EXCEPTION, feedbackId);
        }
            OrganizationReply organizationReply = organizationReplyMapper.dtoMapToOrganizationReply(organizationReplyCreateDTO);
            organizationReply.setFeedback(getFeedbackById(feedbackId));
            return organizationReplyRepository.save(organizationReply).getOrganizationReplyId();
    }

    /**
     * Получить ответ на отзыв от организации
     *
     * @param feedbackId  id отзыва
     * @return  ответ на отзыв от организации(DTO)
     * @exception BusinessException ORGANIZATION_REPLY_NOT_FOUND_EXCEPTION(если ответ организации не найден)
     * @exception BusinessException FEEDBACK_NOT_FOUND_EXCEPTION(если отзыв не найден)
     */
    public OrganizationReplyGetDTO getOrganizationReply(String feedbackId){
        Feedback feedback = getFeedbackById(feedbackId);
        if (!organizationReplyRepository.existsOrganizationReplyByFeedback_feedbackId(feedbackId)){
            throw new BusinessException(Error.ORGANIZATION_REPLY_NOT_FOUND_EXCEPTION, feedbackId);
        }
        return organizationReplyMapper.organizationReplyMapToGetDTO(feedback.getOrganizationReply());
    }
}
