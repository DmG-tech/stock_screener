package dmg.stock_screener.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IndicatorValuesParser {

    private IndicatorValuesParser() {
    }

    private StringBuffer originNumber;

    private StringBuffer originUnit;

    private StringBuffer resultUnit;

    private Double resultNumber;

    public String getResultUnit() {
       return resultUnit == null ? null : resultUnit.toString();
    }

    public Double getResultNumber() {
        return resultNumber;
    }

    public void parse(String s) {
        checkNull(s);

        String correctedString = s.replace(",", "");

        Pattern pattern = Pattern.compile("^(?<number>-*[0-9.]+)(?<unit>[A-Z%]*)");
        Matcher matcher = pattern.matcher(correctedString);

        if (matcher.find()) {

            String tempNumber = matcher.group("number");
            //String tempUnit = matcher.group("unit");

            originNumber = tempNumber.isEmpty() ? null : new StringBuffer(tempNumber);
            originUnit = new StringBuffer(matcher.group("unit")); //tempUnit.isEmpty() ? null : new StringBuffer(tempUnit);

            convert();
        }
    }

    private void checkNull(String s) {
        if (s == null || s.isEmpty())
            throw new IllegalArgumentException(String.format("Input value {%s} don't correct", s));
    }

    private void convert() {
        if (originNumber != null) {

            resultNumber = Double.parseDouble(originNumber.toString());

            switch (originUnit.toString()) {
                case "B":
                    resultNumber = resultNumber * Math.pow(10, 9);
                    resultUnit = new StringBuffer("$");
                    break;
                case "M":
                    resultNumber = resultNumber * Math.pow(10, 6);
                    resultUnit = new StringBuffer("$");
                    break;
                case "K":
                    resultNumber = resultNumber * Math.pow(10, 3);
                    resultUnit = new StringBuffer("$");
                    break;
                case "%":
                    resultUnit = new StringBuffer("%");
                    break;
                default:
                    resultUnit = new StringBuffer();
            }
        }
        round();
    }

    private double round() {
        BigDecimal bd = new BigDecimal(resultNumber);
        bd = bd.setScale(2, RoundingMode.CEILING);
        return bd.doubleValue();
    }
}
