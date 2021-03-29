package com.example.universalstudios.service;

import com.example.universalstudios.dto.ActivityDto;
import com.example.universalstudios.dto.WaitTimeResponse;

public interface ActivityService {

  void addToQueue(final Long rideId, final String accessToken);

  void removeFromQueue(final Long queueId, final String accessToken);

  void recordARide(final ActivityDto activityDto, final String adminAccessToken);

  WaitTimeResponse getWaitingTime(final String accessToken);
}
