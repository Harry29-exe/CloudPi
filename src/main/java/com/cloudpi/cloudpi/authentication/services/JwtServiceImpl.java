package com.cloudpi.cloudpi.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.cloudpi.cloudpi.exception.authentication.AuthenticationException;
import com.cloudpi.cloudpi.exception.authentication.JwtValidationException;
import com.cloudpi.cloudpi.utils.CurrentRequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {
    private static final String TOKEN_TYPE_CLAIM = "Token type";
    private static final String AUTH_TYPE = "Auth";
    private static final String REFRESH_TYPE = "Refresh";
    private static final String DOMAIN = "Domain";


    private final Algorithm authAlgorithm;
    private final Integer authExpInSec;
    private final Algorithm refreshAlgorithm;
    private final Integer refreshExpInSec;
    private final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);


    public JwtServiceImpl(@Value("${cloud-pi.keys.jwt.auth}") String authSecret,
                          @Value("${cloud-pi.keys.jwt.refresh}") String refreshSecret,
                          @Value("${cloud-pi.keys.jwt.auth-expire}") Integer authExp,
                          @Value("${cloud-pi.keys.jwt.refresh-expire}") Integer refreshExp) {

        if (authSecret.getBytes(StandardCharsets.UTF_8).length < (256 / 8)) {
            throw new IllegalArgumentException("Auth secret must be at least 256 bit long");
        }
        if (refreshSecret.getBytes(StandardCharsets.UTF_8).length < (512 / 8)) {
            throw new IllegalArgumentException("Refresh secret must be at least 512 bit long");
        }

        this.authAlgorithm = Algorithm.HMAC256(authSecret.getBytes(StandardCharsets.UTF_8));
        this.authExpInSec = authExp;
        this.refreshAlgorithm = Algorithm.HMAC512(refreshSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshExpInSec = refreshExp;
    }

    @Override
    public String createAuthToken(UserDetails userDetails) {
        var username = userDetails.getUsername();
        if (username == null || username.isBlank()) {
            throw new AuthenticationException();
        }

        return this.createJWT(userDetails.getUsername(), AUTH_TYPE);
    }

    @Override
    public String createRefreshToken(UserDetails userDetails) {
        var username = userDetails.getUsername();
        if (username == null || username.isBlank()) {
            throw new AuthenticationException();
        }

        return this.createJWT(username, REFRESH_TYPE);
    }

    @Override
    public DecodedJWT validateAuthToken(@NonNull String authToken) {
        return validateJWT(authToken, authAlgorithm, AUTH_TYPE);
    }

    @Override
    public String refreshAuthToken(String refreshToken) {
        var token = validateRefreshToken(refreshToken);
        var sub = token.getSubject();

        return this.createJWT(sub, AUTH_TYPE);
    }

    @Override
    public String refreshRefreshToken(String refreshToken) {
        var token = validateRefreshToken(refreshToken);
        var sub = token.getSubject();

        return createJWT(sub, REFRESH_TYPE);
    }

    private String createJWT(@NonNull String subject, @NonNull String tokenType) {
        var now = new Date();
        var exp = new Date(now.getTime() +
                1000L * (tokenType.equals(AUTH_TYPE) ? this.authExpInSec : this.refreshExpInSec)
        );

        Algorithm algorithm;
        if (tokenType.equals(REFRESH_TYPE)) {
            algorithm = refreshAlgorithm;
        } else {
            algorithm = authAlgorithm;
        }


        return JWT.create()
                .withClaim(TOKEN_TYPE_CLAIM, tokenType)
                .withSubject(subject)
                .withClaim(DOMAIN, CurrentRequestUtils.getHeader("Origin"))
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }

    private DecodedJWT validateRefreshToken(String refreshToken) {
        return validateJWT(refreshToken, refreshAlgorithm, REFRESH_TYPE);
    }

    private DecodedJWT validateJWT(String refreshToken, Algorithm algorithm, String tokenType) {
        try {
            var token = JWT.decode(refreshToken);
            var sub = token.getSubject();
            if (sub == null || sub.isBlank()) {
                throw new JWTVerificationException("Subject can not be black or null");
            }


            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(TOKEN_TYPE_CLAIM, tokenType)
                    .withClaim(DOMAIN, CurrentRequestUtils.getHeader("Origin"))
                    .withSubject(sub)
                    .build();

            verifier.verify(token);

            return token;

        } catch (JWTDecodeException ex) {
            this.logNonJwtCompatibleTokenValue(refreshToken, tokenType);
            throw new JwtValidationException();
        } catch (JWTVerificationException ex) {
            this.logInvalidJwtToken(refreshToken, ex.getMessage(), tokenType);
            throw new JwtValidationException();
        }
    }

    private void logNonJwtCompatibleTokenValue(String invalidJWT, String tokenType) {
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            logger.error("Client without ip tried pass following string: " +
                    invalidJWT + " as jwt " + tokenType + "token");
            return;
        }

        var address = CurrentRequestUtils.getPreProxyIp();
        logger.warn("Client with following ip: " + address +
                "tried pass following string: " + invalidJWT +
                " as jwt " + tokenType + "token");
    }

    private void logInvalidJwtToken(String invalidJWT, String rejectReason, String tokenType) {
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            logger.error("Client without ip tried to use following invalid jwt token: " + invalidJWT +
                    " as jwt " + tokenType + "token, token was rejected because of the following reason: " +
                    rejectReason);
            return;
        }
        var address = CurrentRequestUtils.getPreProxyIp();
        logger.warn("Client with following ip: " + address +
                "tried to use following invalid jwt token: " + invalidJWT + " as jwt " + tokenType +
                "token , token was rejected because of the following reason: " + rejectReason);
    }

}
