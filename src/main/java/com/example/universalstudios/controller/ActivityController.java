package com.example.universalstudios.controller;

import static com.example.universalstudios.constants.APIConstants.HEADER_ACCESS_TOKEN;

import com.example.universalstudios.dto.ActivityDto;
import com.example.universalstudios.dto.UserDto;
import com.example.universalstudios.dto.WaitTimeResponse;
import com.example.universalstudios.service.ActivityService;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequestMapping(value = "/v1/activity", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityController {

  @Autowired
  private ActivityService activityService;

  @PostMapping("/queue")
  public ResponseEntity<Void> addToQueue(@RequestParam final Long rideId,
      @RequestHeader(name = HEADER_ACCESS_TOKEN) final String accessToken) {
    this.activityService.addToQueue(rideId, accessToken);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @DeleteMapping("/queue")
  public ResponseEntity<UserDto> removeFromQueue(@RequestParam final Long queueId,
      @RequestHeader(name = HEADER_ACCESS_TOKEN) final String accessToken) {
    this.activityService.removeFromQueue(queueId, accessToken);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/ride")
  public ResponseEntity<Void> recordRide(@RequestBody final @Valid ActivityDto activityDto,
      @RequestHeader(name = HEADER_ACCESS_TOKEN) final String accessToken) {
    this.activityService.recordARide(activityDto, accessToken);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/queue/wait-time")
  public ResponseEntity<WaitTimeResponse> getWaitTimeForUser(
      @RequestHeader(name = HEADER_ACCESS_TOKEN) final String accessToken) {
    return new ResponseEntity<>(this.activityService.getWaitingTime(accessToken), HttpStatus.OK);
  }
}
