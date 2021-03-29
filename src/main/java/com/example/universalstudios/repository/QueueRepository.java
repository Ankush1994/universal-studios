package com.example.universalstudios.repository;

import com.example.universalstudios.entity.Queue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QueueRepository extends JpaRepository<Queue, Long>,
    JpaSpecificationExecutor<Queue>, PagingAndSortingRepository<Queue, Long> {

  Optional<Queue> findByIdAndUserIdAndStatus(final Long id, final Long userId,
      final String status);

  Optional<Queue> findByRideIdAndUserIdAndStatus(final Long rideId, final Long userId,
      final String status);

  List<Queue> findByUserIdInAndRideIdAndStatus(final List<Long> userIds, final Long rideId,
      final String status);

  List<Queue> findByUserIdAndStatus(final Long userId, final String status);

  long countByIdLessThanAndRideIdAndStatus(final Long id, final Long rideId, final String status);

}
