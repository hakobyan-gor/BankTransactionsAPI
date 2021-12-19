package com.estateguru.banktransactions.models.dtos.account;

public record AccountFilterDto (
        long fromDate,
        long toDate
) {
}