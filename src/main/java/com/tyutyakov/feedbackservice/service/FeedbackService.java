package com.tyutyakov.feedbackservice.service;

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

import java.util.List;

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

    /**
     * Проверка наличия отзыва в БД
     *
     * @param feedbackId  id отзыва
     * @return отзыв(entity) или RuntimeException(если не найден)
     */
    public Feedback getFeedbackById(String feedbackId){
        return feedbackRepository.findById(feedbackId).orElseThrow(() -> new RuntimeException());
    }

    /**
     * Найти отзыв по id заказа
     *
     * @param orderID  id заказа
     * @return  отзыв(entity) или RuntimeException(если не найден)
     */
    public Feedback findFeedbackByOrderId(String orderID){
        return feedbackRepository.findFeedbackByOrderID(orderID).orElseThrow(() -> new RuntimeException());
    }

    /**
     * Найти отзыв по id
     *
     * @param feedbackId  id отзыва
     * @return  отзыв(DTO)
     */
    public FeedbackGetDTO findFeedbackById(String feedbackId){
        return feedbackMapper.feedbackMapToFeedbackGetDTO(getFeedbackById(feedbackId));
    }

    /**
     * Создать новый отзыв
     *
     * @param feedbackCreateDTO  отзыв(DTO)
     * @return id созданного отзыва
     */
    @Transactional()
    public String createNewFeedback(FeedbackCreateDTO feedbackCreateDTO){
        try {
            findFeedbackByOrderId(feedbackCreateDTO.getOrderID());
            return "Отзыв к этому заказу уже есть в БД!";
        } catch (RuntimeException e) {
            Feedback feedback = feedbackMapper.feedbackCreateDTOMapToFeedback(feedbackCreateDTO);
            return feedbackRepository.save(feedback).getFeedbackID();
        }
    }

    /**
     * Редактировать отзыв
     *
     * @param feedbackId  id отзыва
     * @param feedbackUpdateDTO  отзыв(DTO)
     * @return  отзыв(DTO)
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
     */
    @Transactional
    public String addCommentToFeedback(String feedbackId, CommentToFeedbackCreateDTO commentToFeedbackCreateDTO){
        Feedback feedback = getFeedbackById(feedbackId);
        CommentToFeedback commentToFeedback = commentToFeedbackMapper.createDtoMapToCommentToFeedback(commentToFeedbackCreateDTO);
        commentToFeedback.setFeedback(feedback);
        return commentToFeedbackRepository.save(commentToFeedback).getCommentID();
    }

    /**
     * Получить все комментарии к отзыву
     *
     * @param feedbackId  id отзыва
     * @return  List комментариев
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
     */
    @Transactional
    public String addOrganizationReply(String feedbackId, OrganizationReplyCreateDTO organizationReplyCreateDTO){
        OrganizationReply organizationReply = organizationReplyMapper.dtoMapToOrganizationReply(organizationReplyCreateDTO);
        organizationReply.setFeedback(getFeedbackById(feedbackId));
        return organizationReplyRepository.save(organizationReply).getOrganizationReplyID();
    }

    /**
     * Получить ответ на отзыв от организации
     *
     * @param feedbackId  id отзыва
     * @return  ответ на отзыв от организации(DTO)
     */
    public OrganizationReplyGetDTO getOrganizationReply(String feedbackId){
        Feedback feedback = getFeedbackById(feedbackId);
        return organizationReplyMapper.organizationReplyMapToGetDTO(feedback.getOrganizationReply());
    }
}
