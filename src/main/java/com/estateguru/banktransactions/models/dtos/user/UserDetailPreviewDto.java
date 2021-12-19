package com.estateguru.banktransactions.models.dtos.user;

public record UserDetailPreviewDto(
        String firstName,
        String lastName,
        String role,
        Long id
) {
}