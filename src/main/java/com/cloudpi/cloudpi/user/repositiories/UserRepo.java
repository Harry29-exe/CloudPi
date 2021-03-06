package com.cloudpi.cloudpi.user.repositiories;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAllByUsernameIn(List<String> usernames);

}
