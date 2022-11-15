package com.buyern.authentication.services;

import com.buyern.authentication.configs.RedisPublishers;
import com.buyern.authentication.configs.SecurityConfiguration;
import com.buyern.authentication.dtos.ResetPasswordDto;
import com.buyern.authentication.dtos.ResponseDTO;
import com.buyern.authentication.enums.TokenType;
import com.buyern.authentication.exceptions.BadRequestException;
import com.buyern.authentication.exceptions.EntityAlreadyExistsException;
import com.buyern.authentication.models.CustomToken;
import com.buyern.authentication.models.UserAuth;
import com.buyern.authentication.dtos.UserSignInDto;
import com.buyern.authentication.notification.EmailRecipient;
import com.buyern.authentication.notification.NotificationModel;
import com.buyern.authentication.notification.NotificationType;
import com.buyern.authentication.notification.RecipientType;
import com.buyern.authentication.repositories.CustomTokenRepository;
import com.buyern.authentication.repositories.UserAuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@Data
@RequiredArgsConstructor
@ConstructorBinding
public class UserAuthService {
    final UserAuthRepository userAuthRepository;
    final CustomTokenRepository customTokenRepository;
    final JwtTokenGeneratorService jwtTokenGeneratorService;
    final TokenService tokenService;
    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final RedisPublishers.Publisher notificationPublisher;
    @Value("${redis.channel.notification.email}")
    String emailNotificationChannel;
    @Value("${jwt.user.token.prefix}")
    private String tokenPrefix;
    @Value("${jwt.user.token.header}")
    private String headerString;
    @Value("${jwt.secret}")
    private String secret;

    /**
     * <h3><b>Register new user.</b></h3>
     *
     * @return {@link String String} format of {@link com.auth0.jwt.JWT JWT}
     */
    @Transactional
    public ResponseDTO.OfString registerUser(UserSignInDto.UserRegistrationDto userRegistrationDto) {
//        check if email or password is registered
        if (userAuthRepository.existsByEmailOrPhoneAllIgnoreCase(userRegistrationDto.getEmail(), userRegistrationDto.getPhone()))
            throw new EntityAlreadyExistsException("User with email or phone already exists");
        UserAuth userAuth = new UserAuth();
        userAuth.setEmail(userRegistrationDto.getEmail().toLowerCase(Locale.ROOT));
        userAuth.setPhone(userRegistrationDto.getPhone());
        userAuth.setId(userRegistrationDto.getId());
        userAuth.setUid(userRegistrationDto.getUid());
        userAuth.setPassword(bCryptPasswordEncoder.encode(userRegistrationDto.getPassword()));
        userAuthRepository.saveAndFlush(userAuth);
        return new ResponseDTO.OfString("00", "Registered Successfully", jwtTokenGeneratorService.generateUserAccessToken(userAuth));
    }

    @Transactional
    public ResponseDTO<Object> forgotPassword(String email) {
        UserAuth userAuth = userAuthRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Email not registered"));
        CustomToken customToken = tokenService.generatePasswordResetToken(userAuth);
        customTokenRepository.deleteByEmailAndType(userAuth.getEmail(), TokenType.PASSWORD_RESET);
        customTokenRepository.flush();
        customTokenRepository.save(customToken);
        String emailContent = "" +
                "<!DOCTYPE html>" +
                "<html lang='en'>" +
                "<head>" +
                "    <meta charset='UTF-8'>" +
                "    <meta http-equiv='X-UA-Compatible' content='IE=edge'>" +
                "    <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                "    <title>Buyern Password Reset Email</title>" +
                "</head>" +
                "<body>" +
                "    <div>" +
                "        <h4>" +
                "            Click link below to reset your Password" +
                "        </h4>" +
                "    </div>" +
                "    <a href='www.gg.com/" + userAuth.getEmail() + "/" + customToken.getToken() + "'>www.gg.com/" + userAuth.getEmail() + "/" + customToken.getToken() + "</a>" +
                "</body>" +
                "</html>";
        NotificationModel<EmailRecipient> notificationModel = new NotificationModel<>();
        notificationModel.setContent(emailContent);
        notificationModel.setTitle("Password Reset");
        notificationModel.setType(NotificationType.MAIL);
        notificationModel.setRecipient(EmailRecipient.builder().email(userAuth.getEmail()).type(RecipientType.USER).uid(userAuth.getUid()).build());
        // send verification mail
        notificationPublisher.publish(notificationModel);
        return new ResponseDTO.OfObject("00", "success: " + "password change token generated and sent to " + userAuth.getEmail(), customToken);

    }

    @Transactional
    public ResponseDTO.OfString resetPassword(ResetPasswordDto resetPasswordDto) {
        CustomToken savedCustomToken = customTokenRepository.findByUserIdAndTokenAndType(resetPasswordDto.getUserId(), resetPasswordDto.getToken(), TokenType.PASSWORD_RESET).orElseThrow(() -> new EntityNotFoundException("token is invalid or token expired"));
        if (savedCustomToken.getExpTime().before(savedCustomToken.getTimeCreated())) {
            customTokenRepository.deleteById(savedCustomToken.getId());
            throw new BadRequestException("Token is Expired");
        }
        userAuthRepository.updatePasswordByUid(SecurityConfiguration.passwordEncoder().encode(resetPasswordDto.getPassword()), resetPasswordDto.getUserId());
        customTokenRepository.deleteById(savedCustomToken.getId());

        return new ResponseDTO.OfString("00", "success", "password changed successfully. you can now login");
    }

    public Map<String, Object> signIn(UserSignInDto userSignInDto, HttpServletResponse response) {
        UserAuth userAuth = userAuthRepository.findByEmail(userSignInDto.getEmail()).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Email is Not Registered"));
        if (!SecurityConfiguration.passwordEncoder().matches(userSignInDto.getPassword(), userAuth.getPassword()))
            throw new BadRequestException("Invalid Password");
        String token = jwtTokenGeneratorService.generateUserAccessToken(userAuth);
        HashMap<String, String> tokens = new HashMap<>();
        response.addHeader(headerString, tokenPrefix + " " + token);
        tokens.put("user", token);
//        DecodedJWT val = JWT.require(Algorithm.HMAC256(secret.getBytes()))
//                .build()
//                .verify(token);
//        log.info(val.getPayload());
//        log.info(val.getClaims().toString());
        ObjectNode responseNode = new ObjectMapper().createObjectNode();
        return Map.of("tokens", tokens);
//        return tokens;
    }
}
