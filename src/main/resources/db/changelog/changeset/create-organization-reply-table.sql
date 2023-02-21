-- liquibase formatted sql

-- changeset tyutyakov:create-response-organization-table
create table if not exists Feedback.organization_reply_table
(
    organization_reply_id          varchar(36)    not null  primary key,
    feedback_id                    varchar(36)    not null  references feedback_table(feedback_id) unique,
    date_time_creation             smalldatetime  not null,
    organization_reply_text        varchar(2000)  not null
)