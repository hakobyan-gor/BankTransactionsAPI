package com.estateguru.banktransactions.models;

public record AuthTokenDTO(
        String token,
        String role
) {
}