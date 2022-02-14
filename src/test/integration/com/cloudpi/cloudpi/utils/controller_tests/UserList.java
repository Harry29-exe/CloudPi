package com.cloudpi.cloudpi.utils.controller_tests;

import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("test & controller-test")
@Configuration
public class UserList {

    @Bean
    public List<PostUserRequest> usersToCreate() {
        return ImmutableList.of(
                new PostUserRequest(
                        "bob",
                        "bob",
                        null,
                        "P@ssword123"
                ),
                new PostUserRequest(
                        "Alice",
                        "Alice",
                        null,
                        "P@ssword321"
                )
        );
    }

}
