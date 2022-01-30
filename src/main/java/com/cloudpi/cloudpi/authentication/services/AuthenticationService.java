package com.cloudpi.cloudpi.authentication.services;

import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    Authentication authenticate(String username, String password);

}
