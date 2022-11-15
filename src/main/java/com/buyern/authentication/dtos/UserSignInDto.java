package com.buyern.authentication.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserSignInDto {
    @NotEmpty(message = "Email can't be empty")
    private String email;
    @NotNull(message = "Password can't be empty")
    private String password;

    @Data
    public static class UserRegistrationDto {
        @NotEmpty(message = "Email can't be empty")
        private String email;
        @NotEmpty(message = "Phone can't be empty")
        private String phone;
        @NotNull(message = "Password can't be empty")
        private String password;
        @NotNull(message = "User Uid can't be empty")
        private String uid;
        @NotNull(message = "User id can't be empty")
        private Long id;
    }
}
