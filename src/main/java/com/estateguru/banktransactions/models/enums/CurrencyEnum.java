package com.estateguru.banktransactions.models.enums;

import java.util.Arrays;
import java.util.Optional;

public enum CurrencyEnum {

    AMD(1),
    EURO(2),
    USD(3),
    GBP(4),
    RUB(5);

    private final int mValue;

    CurrencyEnum(int value) {
        mValue = value;
    }

    public int getValue() {
        return ordinal() + 1;
    }

    private static final CurrencyEnum[] allValues = values();

    public static Optional<CurrencyEnum> valueOf(int value) {
        return Arrays.stream(values())
                .filter(currencyEnum -> currencyEnum.mValue == value)
                .findFirst();
    }

}
