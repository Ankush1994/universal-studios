package com.example.universalstudios.service;

import com.example.universalstudios.constants.USER_ROLE;
import com.example.universalstudios.dto.UserDto;
import com.example.universalstudios.entity.User;
import java.util.List;

public interface UserService {

  UserDto createCustomer(final UserDto userDto, final String adminAccessToken);

  User validateUserAccessToken(final String accessToken, final USER_ROLE userRole);

  List<User> getUsersByTokens(final List<String> tokens);
}
