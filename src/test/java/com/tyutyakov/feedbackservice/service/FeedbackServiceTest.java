package com.tyutyakov.feedbackservice.service;

import com.tyutyakov.feedbackservice.model.dto.*;
import com.tyutyakov.feedbackservice.model.entity.*;
import com.tyutyakov.feedbackservice.model.mapper.*;
import com.tyutyakov.feedbackservice.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
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
    @Spy
    private FeedbackMapper feedbackMapper = new FeedbackMapperImpl();
    @Spy
    private OrganizationReplyMapper organizationReplyMapper = new OrganizationReplyMapperImpl();
    @Spy
    private CommentToFeedbackMapper commentToFeedbackMapper = new CommentToFeedbackMapperImpl();

    @InjectMocks
    private FeedbackService feedbackService;

    private final List<CommentToFeedback> comment = new ArrayList<>();
    private final String feedbackId = "1c5afdce-f34e-445a-9d25-d548b5ad515c";
    private final LocalDateTime localDateTime = LocalDateTime.now();


    private final CommentToFeedback commentToFeedback = new CommentToFeedback("e8f191f9-c133-4901-a44c-99200ec6202a",
                                                                "Вова", localDateTime, "текст", null);
    private final CommentToFeedbackCreateDTO commentToFeedbackCreateDTO = new CommentToFeedbackCreateDTO("Вова", "текст");


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
    void prepare(){
        comment.add(commentToFeedback);
        comment.add(commentToFeedback);
//        feedbackService = new FeedbackService(feedbackRepository,  organizationReplyRepository, commentToFeedbackRepository,
//                new FeedbackMapperImpl(), new OrganizationReplyMapperImpl(), new CommentToFeedbackMapperImpl());
    }

    @Test
    @DisplayName("Проверка наличия отзыва в БД")
    void getFeedbackById() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        Feedback result = feedbackService.getFeedbackById(feedbackId);
        assertEquals(feedback, result);
    }

    @Test
    @DisplayName("Проверка наличия отзыва в БД(ошибка - отзыв не найден)")
    void getFeedbackByIdException() {
        Mockito.doThrow(RuntimeException.class).when(feedbackRepository).findById(any());
        assertThrows(RuntimeException.class, () -> feedbackService.getFeedbackById(feedbackId));
    }

    @Test
    @DisplayName("Найти отзыв по id заказа")
    void findFeedbackByOrderId() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findFeedbackByOrderID(feedbackId);
        Feedback result = feedbackService.findFeedbackByOrderId(feedbackId);
        assertEquals(feedback.getFeedbackID(), result.getFeedbackID());
    }

    @Test
    @DisplayName("Найти отзыв по id заказа(ошибка - отзыв не найден)")
    void findFeedbackByOrderIdException() {
        Mockito.doThrow(RuntimeException.class).when(feedbackRepository).findFeedbackByOrderID(any());
        assertThrows(RuntimeException.class, () -> feedbackService.findFeedbackByOrderId(feedbackId));
    }

    @Test
    @DisplayName("Найти отзыв по id")
    void findFeedbackById() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        FeedbackGetDTO result = feedbackService.findFeedbackById(feedbackId);
        assertEquals(feedbackGetDTO.getOrderID(), result.getOrderID());
        assertEquals(feedbackGetDTO.getFeedbackText(), result.getFeedbackText());
    }

    @Test
    @DisplayName("Создать новый отзыв")
    void createNewFeedback() {
        Mockito.doReturn(feedback).when(feedbackRepository).save(any());
        String resultId = feedbackService.createNewFeedback(feedbackCreateDTO);
        assertEquals(feedback.getFeedbackID(), resultId);
    }

    @Test
    @DisplayName("Создать новый отзыв(ошибка - отзыв существует)")
    void createNewFeedbackException() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findFeedbackByOrderID(any());
        String result = feedbackService.createNewFeedback(feedbackCreateDTO);
        assertEquals("Отзыв к этому заказу уже есть в БД!", result);
    }

    @Test
    @DisplayName("Редактировать отзыв")
    void editFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(any());
        FeedbackGetDTO result = feedbackService.editFeedback(feedbackId, feedbackUpdateDTO);
        assertEquals(feedbackUpdateDTO.getFeedbackText(), result.getFeedbackText());
        assertEquals(feedbackUpdateDTO.getDeliverySpeedAssessment(), result.getDeliverySpeedAssessment());
    }

    @Test
    @DisplayName("Удалить отзыв")
    void deleteFeedbackById() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(any());
        feedbackService.deleteFeedbackById(feedback.getFeedbackID());
        Mockito.verify(feedbackRepository, Mockito.times(1)).deleteById(feedback.getFeedbackID());
    }

    @Test
    @DisplayName("Добавить комментарий к отзыву от другого пользователя")
    void addCommentToFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        Mockito.doReturn(commentToFeedback).when(commentToFeedbackRepository).save(any());
        String result = feedbackService.addCommentToFeedback(feedbackId, commentToFeedbackCreateDTO);
        assertEquals(commentToFeedback.getCommentID(), result);
    }

    @Test
    @DisplayName("Получить все комментарии к отзыву")
    void getAllCommentToFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        List<CommentToFeedbackGetDTO> result = feedbackService.getAllCommentToFeedback(feedbackId);
        assertEquals(feedback.getComments().size(), result.size());
    }

    @Test
    @DisplayName("Оценить отзыв(лайк/дизлайк)")
    void rateFeedback() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        Mockito.doReturn(feedback).when(feedbackRepository).save(feedback);
        Feedback testFeedback = new Feedback();
        testFeedback.setFeedbackRatingLike(feedback.getFeedbackRatingLike());
        testFeedback.setFeedbackRatingDislike(feedback.getFeedbackRatingDislike());
        testFeedback.rateFeedback(feedbackRateDTO.getRate());
        String expectedResult = "Like - " + testFeedback.getFeedbackRatingLike() + ", Dislike - " + testFeedback.getFeedbackRatingDislike();
        String result = feedbackService.rateFeedback(feedbackId, feedbackRateDTO);
        assertEquals(expectedResult, result);
    }

    @Test
    @DisplayName("Добавить ответ на отзыв от организации")
    void addOrganizationReply() {
        Mockito.doReturn(organizationReply).when(organizationReplyRepository).save(any());
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        String result = feedbackService.addOrganizationReply(feedbackId, organizationReplyCreateDTO);
        assertEquals(organizationReply.getOrganizationReplyID(), result);
    }

    @Test
    @DisplayName("Получить ответ на отзыв от организации")
    void getOrganizationReply() {
        Mockito.doReturn(Optional.of(feedback)).when(feedbackRepository).findById(feedbackId);
        OrganizationReplyGetDTO result = feedbackService.getOrganizationReply(feedbackId);
        assertEquals(organizationReply.getOrganizationReplyID(), result.getOrganizationReplyID());
    }
}