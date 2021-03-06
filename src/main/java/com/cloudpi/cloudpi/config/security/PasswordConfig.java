package com.cloudpi.cloudpi.config.security;

import com.cloudpi.cloudpi.validation.password.PasswordSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PasswordConfig {

    public static final List<Character> defaultSpecialChars =
            List.of('!', '@', '#', '$', '%', '^', '&', '_', '-', '+', '?');

    @Bean
    public PasswordSpec getPasswordRequirements() {
        return new PasswordSpec(defaultSpecialChars,
                6, 1, 1, 1, false, 1);
    }

}
