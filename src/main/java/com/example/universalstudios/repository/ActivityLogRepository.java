package com.example.universalstudios.repository;

import com.example.universalstudios.entity.ActivityLog;
import com.example.universalstudios.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long>,
    JpaSpecificationExecutor<ActivityLog>, PagingAndSortingRepository<ActivityLog, Long> {

}
