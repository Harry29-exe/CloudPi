package com.cloudpi.cloudpi.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@Configuration
public class SecurityBeans {

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

//    @Bean
//    public PasswordEncoder createPasswordEncoder() {
//        return new BCryptPasswordEncoder(10);
//    }

}
