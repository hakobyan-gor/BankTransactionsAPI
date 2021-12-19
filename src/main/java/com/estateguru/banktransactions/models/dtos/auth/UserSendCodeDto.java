package com.estateguru.banktransactions.models.dtos.auth;

import javax.validation.constraints.NotNull;

public record UserSendCodeDto (
        @NotNull
        String countryCode,
        @NotNull
        String userName
) {
}