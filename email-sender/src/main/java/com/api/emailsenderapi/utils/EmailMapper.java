package com.api.emailsenderapi.utils;

import com.api.emailsenderapi.model.Email;
import com.api.emailsenderapi.model.dto.EmailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmailMapper {

    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    @Mapping(source = "emailAddress",target = "emailAddress")
    EmailDTO emailToEmailDTO(Email email);
}
