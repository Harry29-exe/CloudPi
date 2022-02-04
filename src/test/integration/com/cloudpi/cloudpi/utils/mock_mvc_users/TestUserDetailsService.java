package com.cloudpi.cloudpi.utils.mock_mvc_users;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestConfiguration
public class TestUserDetailsService {
    private final PasswordEncoder passwordEncoder;

    public TestUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static final String ROOT = "root";
    public static final String MOD = "mod";
    public static final String USER = "root";
    public static final String USER_WITHOUT_PERMISSIONS = "root";

//@WithMockUser
//    @Bean
//    @Primary
//    public UserDetailsService createTestUserService() {
//        var users = List.of(
//            new User(ROOT, )
//        );
//
//        return new InMemoryUserDetailsManager(users);
//    }

}
