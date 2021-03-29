package com.example.universalstudios.mapper;

import com.example.universalstudios.dto.UserDto;
import com.example.universalstudios.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto toDto(final User user);

  User toEntity(final UserDto userDto);
}
