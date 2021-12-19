package com.estateguru.banktransactions.utils;

public class GenerateUniqueIdUtil {

    public static String generateId(Long id) {
        String string = Long.toString(id);
        return switch (string.length()) {
            case 1 -> "000000" + id;
            case 2 -> "00000" + id;
            case 3 -> "0000" + id;
            case 4 -> "000" + id;
            case 5 -> "00" + id;
            case 6 -> "0" + id;
            default -> String.valueOf(id);
        };
    }

}
