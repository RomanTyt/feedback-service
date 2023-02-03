-- liquibase formatted sql

-- changeset tyutyakov:create-response-organization-table
create table if not exists Feedback.response_organization_table
(
    response_organization_id          varchar  not null primary key,
    feedback_id                       varchar  references feedback_table(feedback_id),
    date_time_creation                smalldatetime,
    response_organization_text        varchar
)