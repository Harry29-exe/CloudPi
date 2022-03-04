package com.cloudpi.cloudpi.user.repositiories;

import com.cloudpi.cloudpi.user.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByUser_Username(String username);

}
