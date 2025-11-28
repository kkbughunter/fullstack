package com.application.backend.mapper;

import com.application.backend.dto.LoginResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LoginMapper {

    LoginMapper INSTANCE = Mappers.getMapper(LoginMapper.class);

    @Mapping(source = "token", target = "token")
    @Mapping(source = "refreshToken", target = "refreshToken")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "roleCode", target = "roleCode")
    @Mapping(source = "landingUrl", target = "landingUrl")
    LoginResponseDto toLoginResponseDto(
            String token,
            String refreshToken,
            String userId,
            String roleCode,
            String landingUrl
    );
}
