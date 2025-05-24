package com.dodream.training.util;

import java.text.DecimalFormat;

public class TrainingPriceUtils {

    private static final DecimalFormat OUTPUT_FORMAT = new DecimalFormat("#,###원");

    public static String convertDecimalFormatForPrice(String price){
        try {
            return OUTPUT_FORMAT.format(Integer.parseInt(price));
        } catch (NumberFormatException e) {
            return "잘못된 가격 형식";
        }
    }

    public static String convertDecimalFormatForPrice(int price){
        return OUTPUT_FORMAT.format(price);
    }
}
