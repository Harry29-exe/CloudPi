package com.cloudpi.cloudpi.utils;

import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class CurrentRequestUtils {

    public static HttpServletRequest getCurrentRequest() {
        var requestAttribs = RequestContextHolder.getRequestAttributes();
        if (requestAttribs instanceof ServletRequestAttributes servletAttribs) {
            return servletAttribs.getRequest();
        } else {
            throw new IllegalStateException();
        }
    }

    @Nullable
    public static String getPreProxyIp() {
        return getCurrentRequest().getHeader("X-Real-IP");
    }

    @Nullable
    public static String getHeader(String header) {
        return getCurrentRequest().getHeader(header);
    }

    public static Optional<Authentication> getAuth() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.of(auth);
    }

    public static Optional<String> getCurrentUserUsername() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.of(auth.getName());
    }

}
