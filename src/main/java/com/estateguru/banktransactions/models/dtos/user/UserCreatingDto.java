package com.estateguru.banktransactions.models.dtos.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record UserCreatingDto(
        @NotNull
        String firstName,
        @NotNull
        String lastName,
        @NotNull
        @Email
        String email,
        Long dateOfBirth
) {
}