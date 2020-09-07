package dmg.stock_screener.util;

import dmg.stock_screener.TestMatcher;
import dmg.stock_screener.entities.Indicator;
import org.junit.jupiter.api.Test;

import static dmg.stock_screener.TestMatcher.usingFieldsComparator;
import static dmg.stock_screener.util.IndicatorUtil.setValueAndUnit;
import static dmg.stock_screener.util.TestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IndicatorUtilTest {

    private static TestMatcher<Indicator> MATCHER = usingFieldsComparator(Indicator.class, "id", "name", "date", "company");

    @Test
    void parseStringWithNull() {
        Indicator origin = getNewIndicator();
        assertThrows(IllegalArgumentException.class, () -> setValueAndUnit(origin, ORIGIN_STRING_WITH_NUll));
    }

    @Test
    void parseEmptyString() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_NULL, RESULT_UNIT_NULL);
        setValueAndUnit(origin, ORIGIN_STRING_EMPTY);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithDash() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_NULL, RESULT_UNIT_NULL);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_DASH);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithBillion() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_BILLION, RESULT_UNIT_DOLLARS);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_BILLION);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithMillion() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_MILLION, RESULT_UNIT_DOLLARS);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_MILLION);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithDecimalNumber() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_DECIMAL_NUMBER, RESULT_UNIT_OTHER);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_DECIMAL_NUMBER);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithPercent() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_PERCENT, RESULT_UNIT_PERCENT);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_PERCENT);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithSimpleNumber() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_SIMPLE_NUMBER, RESULT_UNIT_OTHER);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_SIMPLE_NUMBER);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithWord() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_NULL, RESULT_UNIT_NULL);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_WORD);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithNegativeNumber() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_NEGATIVE_NUMBER, RESULT_UNIT_PERCENT);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_NEGATIVE_NUMBER);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithComa() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_WITH_COMMA, RESULT_UNIT_OTHER);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_NUMBER_WITH_COMMA);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithTwoNumbers() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_WITH_TWO_NUMBERS, RESULT_UNIT_PERCENT);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_TWO_NUMBERS);
        MATCHER.assertMatch(origin, result);
    }

    @Test
    void parseStringWithDate() {
        Indicator origin = getNewIndicator();
        Indicator result = getCorrectIndicator(RESULT_NUMBER_NULL, RESULT_UNIT_NULL);
        setValueAndUnit(origin, ORIGIN_STRING_WITH_DATE);
        MATCHER.assertMatch(origin, result);
    }
}