package com.cloudpi.cloudpi.user.service;

import com.cloudpi.cloudpi.exception.resource.ResourceNotExistException;
import com.cloudpi.cloudpi.user.domain.repositiories.UserRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService {
    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var entity = userRepo.findByUsername(username)
                .orElseThrow(ResourceNotExistException::new);
        return new User(entity.getUsername(), entity.getPassword(), List.of());
    }
}
