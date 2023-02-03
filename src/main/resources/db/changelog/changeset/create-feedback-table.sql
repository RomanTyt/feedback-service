-- liquibase formatted sql

-- changeset tyutyakov:create-feedback-table
create table if not exists Feedback.feedback_table
(
    feedback_id                   varchar   not null   primary key,
    date_time_creation            smalldatetime,
    feedback_author_name          varchar(150),
    order_id                      varchar,
    feedback_text                 varchar,
    advantages_of_the_product     varchar,
    disadvantages_of_the_product  varchar,
    delivery_speed_assessment     int check (delivery_speed_assessment>0 and delivery_speed_assessment<6),
    product_quality_assessment    int,
    feedback_rating_like          int,
    feedback_rating_dislike       int
)