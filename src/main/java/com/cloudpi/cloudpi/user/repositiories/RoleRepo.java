package com.cloudpi.cloudpi.user.repositiories;

import com.cloudpi.cloudpi.config.security.Role;
import com.cloudpi.cloudpi.user.domain.RoleEntity;
import com.cloudpi.cloudpi.user.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByUser_Username(String username);

    void deleteAllByUser_UsernameAndRoleIn(String username, List<Role> roles);

}
