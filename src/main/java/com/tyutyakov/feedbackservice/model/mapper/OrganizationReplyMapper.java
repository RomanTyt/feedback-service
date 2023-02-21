package com.tyutyakov.feedbackservice.model.mapper;

import com.tyutyakov.feedbackservice.model.dto.OrganizationReplyCreateDTO;
import com.tyutyakov.feedbackservice.model.dto.OrganizationReplyGetDTO;
import com.tyutyakov.feedbackservice.model.entity.OrganizationReply;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrganizationReplyMapper {
    OrganizationReply dtoMapToOrganizationReply(OrganizationReplyCreateDTO organizationReplyCreateDTO);

    OrganizationReplyCreateDTO organizationReplyMapToDTO(OrganizationReply organizationReply);

    OrganizationReplyGetDTO organizationReplyMapToGetDTO(OrganizationReply organizationReply);
}
