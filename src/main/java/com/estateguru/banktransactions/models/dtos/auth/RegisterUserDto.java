package com.estateguru.banktransactions.models.dtos.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record RegisterUserDto(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        @Size(min = 6)
        String password,
        @NotNull
        @Email
        String email
) {
}
