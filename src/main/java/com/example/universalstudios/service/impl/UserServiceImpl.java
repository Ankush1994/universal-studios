package com.example.universalstudios.service.impl;

import com.example.universalstudios.constants.USER_ROLE;
import com.example.universalstudios.dto.UserDto;
import com.example.universalstudios.entity.User;
import com.example.universalstudios.exception.ServiceException;
import com.example.universalstudios.mapper.UserMapper;
import com.example.universalstudios.repository.UserRepository;
import com.example.universalstudios.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;


  @Override
  public UserDto createCustomer(final UserDto userDto, final String adminAccessToken) {
    this.validateUserAccessToken(adminAccessToken, USER_ROLE.ADMIN);
    final User user = UserMapper.INSTANCE.toEntity(userDto);
    user.setRole(USER_ROLE.CUSTOMER.name());
    user.setAccessToken(UUID.randomUUID().toString());
    return UserMapper.INSTANCE.toDto(this.userRepository.save(user));
  }

  @Override
  public User validateUserAccessToken(final String accessToken, final USER_ROLE userRole) {

    if (accessToken == null) {
      log.error("Access Token not found");
      throw new ServiceException("Access token Not found", HttpStatus.UNAUTHORIZED);
    }

    final Optional<User> optionalUser = this.userRepository
        .findByAccessTokenAndRoleAndActiveTrue(accessToken, userRole.name());

    if (!optionalUser.isPresent()) {
      log.error("Access token doesn't correspond to a user");
      throw new ServiceException("User not found", HttpStatus.UNAUTHORIZED);
    }

    return optionalUser.get();
  }

  @Override
  public List<User> getUsersByTokens(final List<String> tokens) {
    if (CollectionUtils.isEmpty(tokens)) {
      return Collections.emptyList();
    }
    return this.userRepository.findByAccessTokenAndActiveTrue(tokens);
  }
}
