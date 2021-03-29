package com.example.universalstudios.repository;

import com.example.universalstudios.entity.Ride;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RideRepository extends JpaRepository<Ride, Long>,
    JpaSpecificationExecutor<Ride>, PagingAndSortingRepository<Ride, Long> {

  Optional<Ride> findByIdAndActiveTrue(final Long id);
}
