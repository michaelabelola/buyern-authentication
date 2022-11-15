package com.buyern.authentication.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ResetPasswordDto {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String token;
    @NotEmpty
    private String password;

}
