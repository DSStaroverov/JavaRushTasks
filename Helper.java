package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Helper {


    public static String generateRandomString(){
        //create 130 bits random BigInteger
        BigInteger bInt = new BigInteger(130, new SecureRandom());

        //return string representation of BigInteger in 36 radix[a-z]+[0-9]
        return bInt.toString(36);
    }

    public static void printMessage(String message){
        System.out.println(message);
    }
}
