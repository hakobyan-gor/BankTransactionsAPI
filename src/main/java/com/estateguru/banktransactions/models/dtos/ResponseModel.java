package com.estateguru.banktransactions.models.dtos;

import lombok.Builder;

@Builder
public record ResponseModel<T>( Boolean success, T data, String message) {
}