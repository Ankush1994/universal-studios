package com.example.universalstudios.repository;

import com.example.universalstudios.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<User, Long>,
    JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, Long> {

  Optional<User> findByAccessTokenAndRoleAndActiveTrue(final String accessToken, final String role);

  List<User> findByAccessTokenAndActiveTrue(final List<String> accessTokens);

}
