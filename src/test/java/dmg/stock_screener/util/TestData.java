package dmg.stock_screener.util;

import dmg.stock_screener.entities.Indicator;

import static dmg.stock_screener.util.DateUtil.getCurrentDate;

public class TestData {

    public static Indicator INDICATOR = new Indicator(getCurrentDate(), "testEntity", null, null);

    public static final String ORIGIN_STRING_WITH_NUll = null;
    public static final String ORIGIN_STRING_EMPTY = "";
    public static final String ORIGIN_STRING_WITH_DASH = "-";
    public static final String ORIGIN_STRING_WITH_BILLION = "2.98B";
    public static final String ORIGIN_STRING_WITH_MILLION = "499.20M";
    public static final String ORIGIN_STRING_WITH_DECIMAL_NUMBER = "6.19";
    public static final String ORIGIN_STRING_WITH_PERCENT = "0.67%";
    public static final String ORIGIN_STRING_WITH_SIMPLE_NUMBER = "2290";
    public static final String ORIGIN_STRING_WITH_WORD = "Yes";
    public static final String ORIGIN_STRING_WITH_NEGATIVE_NUMBER = "-3.52%";
    public static final String ORIGIN_STRING_WITH_NUMBER_WITH_COMMA = "127,991";
    public static final String ORIGIN_STRING_WITH_TWO_NUMBERS = "2.46% 2.36%";
    public static final String ORIGIN_STRING_WITH_DATE = "Aug 06 AMC";

    public static String RESULT_UNIT_NULL = null;
    public static String RESULT_UNIT_DOLLARS = "$";
    public static String RESULT_UNIT_PERCENT = "%";
    public static String RESULT_UNIT_OTHER = "";

    public static Double RESULT_NUMBER_NULL = null;
    public static Double RESULT_NUMBER_BILLION = 2980000000.00;
    public static Double RESULT_NUMBER_MILLION = 499200000.00;
    public static Double RESULT_NUMBER_DECIMAL_NUMBER = 6.19;
    public static Double RESULT_NUMBER_PERCENT = 0.67;
    public static Double RESULT_NUMBER_SIMPLE_NUMBER = 2290.00;
    public static Double RESULT_NUMBER_NEGATIVE_NUMBER = -3.52;
    public static Double RESULT_NUMBER_WITH_COMMA = 127.99;
    public static Double RESULT_WITH_TWO_NUMBERS = 2.46;

    public static Indicator getNewIndicator() {
        return new Indicator();
    }

    public static Indicator getCorrectIndicator(Double value, String unit) {
        return new Indicator(null, null, value, unit);
    }
}
