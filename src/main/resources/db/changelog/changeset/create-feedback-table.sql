-- liquibase formatted sql

-- changeset tyutyakov:create-feedback-table
create table if not exists Feedback.feedback_table
(
    feedback_id                   varchar(36)    not null   primary key,
    date_time_creation            smalldatetime  not null,
    date_time_update              smalldatetime  not null,
    feedback_author_name          varchar(100)   not null,
    order_id                      varchar(36)    not null,
    feedback_text                 varchar(2000),
    advantages_of_the_product     varchar(2000),
    disadvantages_of_the_product  varchar(2000),
    delivery_speed_assessment     int            check (delivery_speed_assessment>0 and delivery_speed_assessment<6),
    product_quality_assessment    int            check (product_quality_assessment>0 and product_quality_assessment<6),
    feedback_rating_like          int,
    feedback_rating_dislike       int
)