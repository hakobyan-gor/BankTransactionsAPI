package com.estateguru.banktransactions.models.dtos.user;

public record UserListPreviewDto(
        String firstName,
        Long createdDate,
        String lastName,
        String email,
        Long id
        ) {
}
