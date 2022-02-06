package com.cloudpi.cloudpi.config.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN,
    MODERATOR,
    USER,
    BOT;

    public static final String admin = "ROLE_ADMIN";
    public static final String moderator = "ROLE_MODERATOR";
    public static final String user = "ROLE_USER";
    public static final String bot = "ROLE_BOT";


    @Override
    public String getAuthority() {
        return "ROLE_"+this.name();
    }

}
