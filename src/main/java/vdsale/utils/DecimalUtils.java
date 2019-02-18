package vdsale.utils;

import java.text.DecimalFormat;

public class DecimalUtils {

    private static DecimalFormat df = new DecimalFormat("#.##");

    public static String formatTwoDecimalPlacesToString(double value){
        return df.format(value).replace(",", ".");
    }

    public static double formatTwoDecimalPlacesToDouble(double value){
        return Double.valueOf(formatTwoDecimalPlacesToString(value)).doubleValue();
    }

}
