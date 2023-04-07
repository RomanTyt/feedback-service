package com.tyutyakov.feedbackservice.service;

import com.tyutyakov.feedbackservice.exception.error.exception.FeedbackExistException;
import com.tyutyakov.feedbackservice.exception.error.exception.FeedbackNotFoundException;
import com.tyutyakov.feedbackservice.model.dto.*;
import com.tyutyakov.feedbackservice.model.entity.*;
import com.tyutyakov.feedbackservice.model.mapper.*;
import com.tyutyakov.feedbackservice.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith({
        MockitoExtension.class
})
class FeedbackServiceTest {
    @Mock
    private FeedbackRepository feedbackRepository;
    @Mock
    private OrganizationReplyRepository organizationReplyRepository ;
    @Mock
    private CommentToFeedbackRepository commentToFeedbackRepository;

    private FeedbackService sut;

    private final String feedbackId = "1c5afdce-f34e-445a-9d25-d548b5ad515c";
    private final LocalDateTime localDateTime = LocalDateTime.now();

    private final List<CommentToFeedback> comment = new ArrayList<>();
    private final CommentToFeedbackCreateDTO commentToFeedbackCreateDTO = new CommentToFeedbackCreateDTO("Вова", "текст");
    private final CommentToFeedback commentToFeedback = new CommentToFeedback("e8f191f9-c133-4901-a44c-99200ec6202a",
            "Вова", localDateTime, "текст", null);

    private final OrganizationReply organizationReply = new OrganizationReply("1212121-222222-333333-444444", localDateTime, "", null);
    private final OrganizationReplyCreateDTO organizationReplyCreateDTO = new OrganizationReplyCreateDTO("текст ответа");

    private final FeedbackRateDTO feedbackRateDTO = new FeedbackRateDTO(true);
    private final FeedbackCreateDTO feedbackCreateDTO = new FeedbackCreateDTO("Вася", "1111111-222222-333333-444444",
            "Всё хорошо", "Есть", "Нет", 4, 5);
    private final Feedback feedback = new Feedback("1c5afdce-f34e-445a-9d25-d548b5ad515c", localDateTime, localDateTime, "Вася",
            "1111111-222222-333333-444444", "Всё хорошо", "Есть", "Нет",
            4, 5, 15, 40, comment, organizationReply);
    private final FeedbackGetDTO feedbackGetDTO = new FeedbackGetDTO(localDateTime, localDateTime, "Вася",
            "1111111-222222-333333-444444", "Всё хорошо", "Есть", "Нет",
            4, 5, 15, 40);
    private final FeedbackUpdateDTO feedbackUpdateDTO = new FeedbackUpdateDTO("Всё плохо", "Нет", "Есть",
            2, 1);

    @BeforeEach
    void setUp(){
        comment.add(commentToFeedback);
        comment.add(commentToFeedback);
        sut = new FeedbackService(feedbackRepository,  organizationReplyRepository, commentToFeedbackRepository,
                new FeedbackMapperImpl(), new OrganizationReplyMapperImpl(), new CommentToFeedbackMapperImpl());
    }

    @Test
    @DisplayName("Проверка наличия отзыва в БД")
    void getFeedbackById() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        Feedback result = sut.getFeedbackById(feedbackId);
        assertEquals(feedback, result);
    }

    @Test
    @DisplayName("Проверка наличия отзыва в БД(ошибка - отзыв не найден)")
    void getFeedbackByIdException() {
        Mockito.doThrow(FeedbackNotFoundException.class).when(feedbackRepository).findById(any());
        assertThrows(FeedbackNotFoundException.class, () -> sut.getFeedbackById(feedbackId));
    }

    @Test
    @DisplayName("Найти отзыв по id заказа")
    void findFeedbackByOrderId() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findFeedbackByOrderID(feedbackId);
        Feedback result = sut.findFeedbackByOrderId(feedbackId);
        assertEquals(feedback.getFeedbackID(), result.getFeedbackID());
    }

    @Test
    @DisplayName("Найти отзыв по id заказа(ошибка - отзыв не найден)")
    void findFeedbackByOrderIdException() {
        Mockito.doThrow(FeedbackNotFoundException.class).when(feedbackRepository).findFeedbackByOrderID(any());
        assertThrows(FeedbackNotFoundException.class, () -> sut.findFeedbackByOrderId(feedbackId));
    }

    @Test
    @DisplayName("Найти отзыв по id")
    void findFeedbackById() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        FeedbackGetDTO result = sut.findFeedbackById(feedbackId);
        assertEquals(feedbackGetDTO.getOrderID(), result.getOrderID());
        assertEquals(feedbackGetDTO.getFeedbackText(), result.getFeedbackText());
    }

    @Test
    @DisplayName("Создать новый отзыв")
    void createNewFeedback() {
        Mockito.doReturn(feedback).when(feedbackRepository).save(any());
        String resultId = sut.createNewFeedback(feedbackCreateDTO);
        assertEquals(feedback.getFeedbackID(), resultId);
    }

    @Test
    @DisplayName("Создать новый отзыв(ошибка - отзыв существует)")
    void createNewFeedbackException() {
        Mockito.doReturn(true).when(feedbackRepository).existsFeedbackByOrderID(any());
        assertThrows(FeedbackExistException.class, () -> sut.createNewFeedback(feedbackCreateDTO));
    }

    @Test
    @DisplayName("Редактировать отзыв")
    void editFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(any());
        FeedbackGetDTO result = sut.editFeedback(feedbackId, feedbackUpdateDTO);
        assertEquals(feedbackUpdateDTO.getFeedbackText(), result.getFeedbackText());
        assertEquals(feedbackUpdateDTO.getDeliverySpeedAssessment(), result.getDeliverySpeedAssessment());
    }

    @Test
    @DisplayName("Удалить отзыв")
    void deleteFeedbackById() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(any());
        sut.deleteFeedbackById(feedback.getFeedbackID());
        Mockito.verify(feedbackRepository, Mockito.times(1)).deleteById(feedback.getFeedbackID());
    }

    @Test
    @DisplayName("Добавить комментарий к отзыву от другого пользователя")
    void addCommentToFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        Mockito.doReturn(commentToFeedback).when(commentToFeedbackRepository).save(any());
        String result = sut.addCommentToFeedback(feedbackId, commentToFeedbackCreateDTO);
        assertEquals(commentToFeedback.getCommentID(), result);
    }

    @Test
    @DisplayName("Получить все комментарии к отзыву")
    void getAllCommentToFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        List<CommentToFeedbackGetDTO> result = sut.getAllCommentToFeedback(feedbackId);
        assertEquals(feedback.getComments().size(), result.size());
    }

    @Test
    @DisplayName("Оценить отзыв(лайк/дизлайк)")
    void rateFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        Mockito.doReturn(feedback).when(feedbackRepository).save(feedback);
        Feedback testFeedback = getTestFeedback();
        String expectedResult = "Like - " + testFeedback.getFeedbackRatingLike() + ", Dislike - " + testFeedback.getFeedbackRatingDislike();
        String result = sut.rateFeedback(feedbackId, feedbackRateDTO);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Добавить ответ на отзыв от организации")
    void addOrganizationReply() {
        Mockito.doReturn(organizationReply).when(organizationReplyRepository).save(any());
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        String result = sut.addOrganizationReply(feedbackId, organizationReplyCreateDTO);
        assertEquals(organizationReply.getOrganizationReplyID(), result);
    }

    @Test
    @DisplayName("Получить ответ на отзыв от организации")
    void getOrganizationReply() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        OrganizationReplyGetDTO result = sut.getOrganizationReply(feedbackId);
        assertEquals(organizationReply.getOrganizationReplyID(), result.getOrganizationReplyID());
    }

    private Feedback getTestFeedback() {
        Feedback testFeedback = new Feedback();
        testFeedback.setFeedbackRatingLike(feedback.getFeedbackRatingLike());
        testFeedback.setFeedbackRatingDislike(feedback.getFeedbackRatingDislike());
        testFeedback.rateFeedback(feedbackRateDTO.getRate());
        return testFeedback;
    }
}