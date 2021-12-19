package com.estateguru.banktransactions.models.dtos.auth;

import javax.validation.constraints.NotNull;

public record LoginUserDto(
        @NotNull
        String username,
        @NotNull
        String password
) {
}