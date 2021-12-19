package com.estateguru.banktransactions.utils;

import java.util.Random;

public class GenerateCodeUtil {

    public static String generateCode(int length) {
        String randomNumber = String.valueOf(Math.abs(new Random().nextInt()));
        String code = randomNumber;
        if (randomNumber.length() > length)
            code = randomNumber.substring(0, length);
        return code;
    }

}
