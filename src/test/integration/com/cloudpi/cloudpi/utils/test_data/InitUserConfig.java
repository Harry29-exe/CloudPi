package com.cloudpi.cloudpi.utils.test_data;

import com.cloudpi.cloudpi.user.api.requests.PostUserRequest;
import com.cloudpi.cloudpi.user.service.dto.CreateUser;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("test")
@Configuration
public class InitUserConfig {

    @Bean
    public List<PostUserRequest> usersToCreateRequest() {
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

    @Bean
    public List<CreateUser> usersToCreate() {
        return ImmutableList.of(
                new CreateUser(
                        "bob",
                        "bob",
                        null,
                        "P@ssword123"
                ),
                new CreateUser(
                        "Alice",
                        "Alice",
                        null,
                        "P@ssword321"
                )
        );
    }

}
