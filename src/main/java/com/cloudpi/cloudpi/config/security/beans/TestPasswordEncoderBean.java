package com.cloudpi.cloudpi.config.security.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("test")
@Configuration
public class TestPasswordEncoderBean {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        return new SCryptPasswordEncoder(2, 2, 2, 32, 64);
        return new BCryptPasswordEncoder(4);
    }

}
