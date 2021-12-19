package com.estateguru.banktransactions.models.dtos.account;

import com.estateguru.banktransactions.models.enums.CurrencyEnum;
import lombok.Getter;

@Getter
public class AccountListPreviewDto {

    private Long id;
    private String accountNumber;
    private int currency;
    private double cost;

    public AccountListPreviewDto(Long id, String accountNumber, CurrencyEnum currency, double cost) {
        this.id            = id;
        this.accountNumber = accountNumber;
        this.currency      = currency.getValue();
        this.cost          = cost;
    }

}