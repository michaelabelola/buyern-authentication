package com.buyern.authentication.controllers;

import com.buyern.authentication.dtos.ResetPasswordDto;
import com.buyern.authentication.dtos.ResponseDTO;
import com.buyern.authentication.dtos.UserSignInDto;
import com.buyern.authentication.services.UserAuthService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

@Data
@RestController
@RequestMapping("api/v1/")
public class UserAuthController {
    final UserAuthService userAuthService;

    @PostMapping("user/signIn")
    public ResponseEntity<Map<String, Object>> signIn(@Valid @RequestBody UserSignInDto userSignInDto, HttpServletResponse response) {
        userSignInDto.setEmail(userSignInDto.getEmail().toLowerCase(Locale.ROOT));
        return ResponseEntity.ok(userAuthService.signIn(userSignInDto, response));
    }
//returns user JWT
    @PostMapping("user/register")
    public ResponseDTO.OfString registerUser(@Valid @RequestBody UserSignInDto.UserRegistrationDto userRegistrationDto) {
        return userAuthService.registerUser(userRegistrationDto);
    }

    @PostMapping("user/forgotPassword/{email}")
    public ResponseDTO<Object> forgotPassword(@PathVariable(value = "email") String email) {
        return userAuthService.forgotPassword(email.toLowerCase(Locale.ROOT));
    }

    @PostMapping("user/resetPassword")
    public ResponseDTO<String> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto) {
        return userAuthService.resetPassword(resetPasswordDto);
    }
}
