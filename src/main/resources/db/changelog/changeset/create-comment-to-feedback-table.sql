-- liquibase formatted sql

-- changeset tyutyakov:create-comment-to-feedback-table
create table if not exists Feedback.comment_to_feedback_table
(
    comment_id                       varchar  not null primary key,
    feedback_id                      varchar references feedback_table(feedback_id),
    commentator_name                 varchar(150),
    date_time_creation               smalldatetime,
    comment_text                     varchar
)