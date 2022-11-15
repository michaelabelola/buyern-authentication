package com.buyern.authentication.services;

import com.buyern.authentication.enums.TokenType;
import com.buyern.authentication.models.CustomToken;
import com.buyern.authentication.models.UserAuth;
import com.buyern.authentication.repositories.CustomTokenRepository;
import com.buyern.authentication.repositories.UserAuthRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.buyern.authentication.enums.TokenType.PASSWORD_RESET;

@Service
@Data
public class TokenService {
    final CustomTokenRepository customTokenRepository;
    final UserAuthRepository userAuthRepository;
//    @Value("${password.reset.token.ttl}")
    Long passwordResetTokenTtl = 86_400_000L;

    public CustomToken generateToken(TokenType tokenType, String email, String userId) {
        CustomToken customToken = new CustomToken();
        customToken.setToken(UUID.randomUUID().toString());
        customToken.setEmail(email);
        customToken.setType(tokenType);
        customToken.setUserId(userId);
        return customToken;
    }

    public CustomToken generatePasswordResetToken(UserAuth userAuth) {

        CustomToken customToken = new CustomToken();
        customToken.setToken(UUID.randomUUID().toString());
        customToken.setEmail(userAuth.getEmail());
        customToken.setType(PASSWORD_RESET);
        customToken.setUserId(userAuth.getUid());
        customToken.setExpTime(new Date(System.currentTimeMillis() + passwordResetTokenTtl));
        return customToken;

    }

}
