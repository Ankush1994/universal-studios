package com.example.universalstudios.controller;

import static com.example.universalstudios.constants.APIConstants.HEADER_ACCESS_TOKEN;

import com.example.universalstudios.dto.UserDto;
import com.example.universalstudios.service.UserService;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping("/customer")
  public ResponseEntity<UserDto> createCustomer(@RequestBody final @Valid UserDto userDto,
      @RequestHeader(name = HEADER_ACCESS_TOKEN) final String adminAccessToken) {
    return new ResponseEntity<>(this.userService.createCustomer(userDto, adminAccessToken),
        HttpStatus.OK);
  }
}
