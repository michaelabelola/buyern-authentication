package com.buyern.authentication.controllers;

import com.buyern.authentication.services.TokenService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Data
@RestController
@RequestMapping("api/v1/token/")
public class TokenController {
    final TokenService tokenService;

    @PostMapping("generate")
    public String generateToken() {
        return null;
    }

    @PostMapping("generate/PasswordReset/")
    public String generatePasswordResetToken() {
        return null;
    }
}
