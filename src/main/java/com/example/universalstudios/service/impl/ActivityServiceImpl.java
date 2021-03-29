package com.example.universalstudios.service.impl;

import com.example.universalstudios.constants.QUEUE_STATUS;
import com.example.universalstudios.constants.USER_ROLE;
import com.example.universalstudios.dto.ActivityDto;
import com.example.universalstudios.dto.WaitTimeDto;
import com.example.universalstudios.dto.WaitTimeResponse;
import com.example.universalstudios.entity.ActivityLog;
import com.example.universalstudios.entity.Queue;
import com.example.universalstudios.entity.Ride;
import com.example.universalstudios.entity.User;
import com.example.universalstudios.exception.ServiceException;
import com.example.universalstudios.repository.ActivityLogRepository;
import com.example.universalstudios.repository.QueueRepository;
import com.example.universalstudios.repository.RideRepository;
import com.example.universalstudios.service.ActivityService;
import com.example.universalstudios.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Log4j2
@Service
public class ActivityServiceImpl implements ActivityService {

  @Autowired
  private UserService userService;

  @Autowired
  private RideRepository rideRepository;

  @Autowired
  private QueueRepository queueRepository;

  @Autowired
  private ActivityLogRepository activityLogRepository;

  @Override
  public void addToQueue(final Long rideId, final String accessToken) {
    final User user = this.userService.validateUserAccessToken(accessToken, USER_ROLE.CUSTOMER);
    final Ride ride = this.validateRide(rideId);

    final Optional<Queue> optionalQueue = this.queueRepository
        .findByRideIdAndUserIdAndStatus(rideId, user.getId(), QUEUE_STATUS.ADDED.name());
    if (optionalQueue.isPresent()) {
      log.error("User is already in Queue");
      throw new ServiceException("Already in Queue", HttpStatus.BAD_REQUEST);
    }

    final Queue queue = new Queue();
    queue.setRideId(ride.getId());
    queue.setUserId(user.getId());
    queue.setStatus(QUEUE_STATUS.ADDED.name());
    this.queueRepository.save(queue);
  }

  @Override
  public void removeFromQueue(final Long queueId, final String accessToken) {
    final User user = this.userService.validateUserAccessToken(accessToken, USER_ROLE.CUSTOMER);
    final Optional<Queue> optionalQueue = this.queueRepository
        .findByIdAndUserIdAndStatus(queueId, user.getId(), QUEUE_STATUS.ADDED.name());
    if (!optionalQueue.isPresent()) {
      log.error("No Queue Entry found");
      throw new ServiceException("Entry in queue not found", HttpStatus.BAD_REQUEST);
    }

    final Queue queue = optionalQueue.get();
    queue.setStatus(QUEUE_STATUS.DELETED.name());
    this.queueRepository.save(queue);
  }

  @Override
  @Transactional
  public void recordARide(final ActivityDto activityDto, final String adminAccessToken) {
    this.userService.validateUserAccessToken(adminAccessToken, USER_ROLE.ADMIN);
    final Ride ride = this.validateRide(activityDto.getRideId());

    if (!CollectionUtils.isEmpty(activityDto.getUserIds())) {
      final List<Queue> queueEntries = this.queueRepository
          .findByUserIdInAndRideIdAndStatus(activityDto.getUserIds(), activityDto.getRideId(),
              QUEUE_STATUS.ADDED.name());
      if (!CollectionUtils.isEmpty(queueEntries)) {
        for (Queue queue : queueEntries) {
          queue.setStatus(QUEUE_STATUS.PROCESSED.name());
        }
        this.queueRepository.saveAll(queueEntries);
      }
    }

    final ActivityLog activity = new ActivityLog();
    activity.setRideId(ride.getId());
    activity.setUserId(0L);
    this.activityLogRepository.save(activity);
  }

  @Override
  public WaitTimeResponse getWaitingTime(final String accessToken) {
    final User user = this.userService.validateUserAccessToken(accessToken, USER_ROLE.CUSTOMER);
    final WaitTimeResponse waitTimeResponse = new WaitTimeResponse();
    waitTimeResponse.setUserId(user.getId());
    waitTimeResponse.setUserName(user.getName());
    final List<Queue> queueListForUser = this.queueRepository
        .findByUserIdAndStatus(user.getId(), QUEUE_STATUS.ADDED.name());
    if (!CollectionUtils.isEmpty(queueListForUser)) {
      final List<WaitTimeDto> waitTimes = new ArrayList<>();
      for (Queue queueEntry : queueListForUser) {
        final Ride ride = this.validateRide(queueEntry.getRideId());
        final Long peopleInFrontForRide = this.queueRepository
            .countByIdLessThanAndRideIdAndStatus(queueEntry.getId(), ride.getId(),
                QUEUE_STATUS.ADDED.name());
        final WaitTimeDto waitTimeForRide = new WaitTimeDto();
        waitTimeForRide.setQueueId(queueEntry.getId());
        waitTimeForRide.setRideId(ride.getId());
        waitTimeForRide.setRideName(ride.getName());
        waitTimeForRide.setPositionInQueue(peopleInFrontForRide.intValue() + 1);
        waitTimeForRide.setWaitTimeInSec(
            (peopleInFrontForRide.intValue() / ride.getCapacity()) * ride.getRoundDurationInSec());
        waitTimes.add(waitTimeForRide);
      }
      waitTimeResponse.setWaitTimes(waitTimes);
    }

    return waitTimeResponse;
  }

  private Ride validateRide(final Long rideId) {

    if (rideId == null) {
      log.error("Ride ID not found");
      throw new ServiceException("Ride ID Not found", HttpStatus.BAD_REQUEST);
    }

    final Optional<Ride> optionalRide = this.rideRepository.findByIdAndActiveTrue(rideId);

    if (!optionalRide.isPresent()) {
      log.error("Ride ID doesn't correspond to any ride");
      throw new ServiceException("Ride not found", HttpStatus.BAD_REQUEST);
    }

    return optionalRide.get();
  }

}
