-- liquibase formatted sql

-- changeset tyutyakov:create-comment-to-feedback-table
create table if not exists Feedback.comment_to_feedback_table
(
    comment_id                       varchar(36)     not null   primary key,
    feedback_id                      varchar(36)     not null   references feedback_table(feedback_id),
    commentator_name                 varchar(100)    not null,
    date_time_creation               smalldatetime   not null,
    comment_text                     varchar(2000)
)