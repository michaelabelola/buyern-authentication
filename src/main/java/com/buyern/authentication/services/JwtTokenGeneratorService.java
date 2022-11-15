package com.buyern.authentication.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.buyern.authentication.models.UserAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenGeneratorService {
    @Value("${jwt.secret}")
    private String secret;

    /**
     * <h3>Generate new User {@link JWT JWT}</h3>
     *
     * @return {@link String String} format of signed {@link JWT JWT}
     */
    public String generateUserAccessToken(UserAuth userAuth) {
        return JWT.create()
                .withJWTId(userAuth.getUid())
                .withHeader(Map.of("accessType", "USER_ACCESS", "version", "1.0.0"))
                .withClaim("ENTITY_REGISTRATION", true)
                .withClaim("ALL_ACCESS", true)
                //expires in 31 days
                .withExpiresAt(new Date(System.currentTimeMillis() + 2678400000L))
                .sign(Algorithm.HMAC256(secret));
    }
}
