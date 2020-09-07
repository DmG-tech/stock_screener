package dmg.stock_screener.util;

import dmg.stock_screener.entities.Indicator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dmg.stock_screener.util.ValidationUtil.checkNull;

public class IndicatorUtil {

    private static StringBuffer unit;

    private IndicatorUtil() {
    }

    public static boolean isCorrectData(Indicator indicator) {
        return indicator.getName() != null && !indicator.getName().isEmpty();
    }

    public static void setValueAndUnit(Indicator indicator, String value) {

        checkNull(value, "string with value for indicator");

        String correctedString = fixDecimalSeparator(value);

        Pattern pattern = Pattern.compile("^(?<number>-*[0-9.]+)(?<unit>[A-Z%]*)");
        Matcher matcher = pattern.matcher(correctedString);

        if (matcher.find()) {

            String number = matcher.group("number");
            unit = new StringBuffer(matcher.group("unit"));

            double result = convert(number);
            result = round(result);

            indicator.setValue(result);
            indicator.setUnit(unit.toString());
        }
    }

    private static String fixDecimalSeparator(String value) {
        return value.replaceAll(",", "");
    }

    private static double convert(String number) {

        double resultNumber = Double.parseDouble(number);

        switch (unit.toString()) {
            case "B" -> {
                resultNumber = resultNumber * Math.pow(10, 9);
                unit = new StringBuffer("$");
            }
            case "M" -> {
                resultNumber = resultNumber * Math.pow(10, 6);
                unit = new StringBuffer("$");
            }
            case "K" -> {
                resultNumber = resultNumber * Math.pow(10, 3);
                unit = new StringBuffer("$");
            }
            case "%" -> unit = new StringBuffer("%");
            default -> unit = new StringBuffer();
        }
        return resultNumber;
    }

    private static double round(double number) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
