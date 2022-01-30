package com.cloudpi.cloudpi.authentication.api;

import com.cloudpi.cloudpi.authentication.api.dto.LoginRequest;
import com.cloudpi.cloudpi.authentication.services.AuthenticationService;
import com.cloudpi.cloudpi.authentication.services.JwtService;
import com.cloudpi.cloudpi.exception.authentication.AuthenticationException;
import com.cloudpi.cloudpi.exception.request.NoRequiredCookieException;
import com.cloudpi.cloudpi.exception.request.NoRequiredHeaderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

//@CrossOrigin(
//        origins = {FRONTEND_ADDRESS, "https://192.168.0.185:4430"},
//        exposedHeaders = "Authorization",
//        allowCredentials = "true",
//        methods = {RequestMethod.POST, RequestMethod.OPTIONS})
@RestController
public class AuthenticationApiController implements AuthenticationApi {
    private final AuthenticationService authService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    private final SecureRandom secureRandom = SecureRandom.getInstanceStrong();

    public static final String REFRESH_TOKEN_COOKIE_NAME = "Refresh-Token";
    public static final String REFRESH_TOKEN_PATH = "/api/refresh";
    private final Integer refreshTokenExpiresTimeInSec;

    public AuthenticationApiController(
            AuthenticationService authService,
            UserDetailsService userDetailsService,
            JwtService jwtService,
            @Value("${jwt.config.refresh.exp_in_sec}") Integer refreshExp
    ) throws NoSuchAlgorithmException {
        this.authService = authService;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.refreshTokenExpiresTimeInSec = refreshExp;
    }

    @Override
    public void login(Boolean dontLogout, LoginRequest requestBody, HttpServletRequest request, HttpServletResponse response) {

        authService.authenticate(requestBody.username(), requestBody.password());

        var user = userDetailsService.loadUserByUsername(requestBody.username());


        var authToken = jwtService.createAuthToken(user);
        response.setHeader("Authorization", authToken);

        var refreshToken = jwtService.createRefreshToken(user);
        this.addRefreshTokenCookie(refreshToken, response, !dontLogout);

        this.delayLogin();
    }

    @Override
    public void logout(HttpServletResponse response) {
        addDeleteRefreshTokenCookie(response);
    }

    @Override
    public void refreshAuthToken(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = getRefreshToken(request);
        var newAuthToken = jwtService.refreshAuthToken(refreshToken.getValue());

        response.setHeader("Authorization", newAuthToken);
    }

    @Override
    public void refreshRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        var refreshToken = getRefreshToken(request);
        var newRefreshToken = jwtService.refreshRefreshToken(refreshToken.getValue());

        addRefreshTokenCookie(newRefreshToken, response, false);
    }

    private Cookie getRefreshToken(HttpServletRequest request) {
        var cookies = request.getCookies();
        if (cookies == null) {
            throw new NoRequiredCookieException(REFRESH_TOKEN_COOKIE_NAME);
        }

        var refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_COOKIE_NAME))
                .findFirst()
                .orElseThrow(() -> new NoRequiredHeaderException(REFRESH_TOKEN_COOKIE_NAME));

        if(refreshToken == null ||
                refreshToken.getValue() == null ||
                refreshToken.getValue().isBlank()
        ) {
            throw new NoRequiredCookieException(REFRESH_TOKEN_COOKIE_NAME);
        }

        return refreshToken;
    }

    private void addRefreshTokenCookie(
            String refreshToken,
            HttpServletResponse response,
            boolean shouldExpireAtSessionEnd
    ) {
        var cookie = ResponseCookie
                .from(REFRESH_TOKEN_COOKIE_NAME, refreshToken)
                .path(REFRESH_TOKEN_PATH)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(shouldExpireAtSessionEnd ? -1 : refreshTokenExpiresTimeInSec)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    private void addDeleteRefreshTokenCookie(HttpServletResponse response) {
        var cookie = ResponseCookie
                .from(REFRESH_TOKEN_COOKIE_NAME, "")
                .path(REFRESH_TOKEN_PATH)
                .sameSite("None")
                .httpOnly(true)
                .secure(true)
                .maxAge(0L)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }


    private void delayLogin() {
        var randomTimeout = secureRandom.nextInt(0, 201);

        try {
            TimeUnit.MILLISECONDS.sleep(400 + randomTimeout);
        } catch (InterruptedException ex) {
            throw new AuthenticationException();
        }
    }


}
