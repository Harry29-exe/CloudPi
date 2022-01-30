package com.cloudpi.cloudpi.user.domain.repositiories;

import com.cloudpi.cloudpi.user.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
