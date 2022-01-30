package com.cloudpi.cloudpi.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    MODERATOR,
    USER,
    BOT;

    public static final String admin = "ADMIN";
    public static final String moderator = "MODERATOR";
    public static final String user = "USER";
    public static final String bot = "BOT";


    @Override
    public String getAuthority() {
        return this.name();
    }
}
