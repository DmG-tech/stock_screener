package dmg.stock_screener.entities;

import java.util.Collections;
import java.util.List;

import static dmg.stock_screener.util.DateUtil.getCurrentDate;

public class TestDataIndicator {

    public static final Company COMPANY_CORRECT = new Company(1, "Corporation of Evil", "IT", "COE", Collections.EMPTY_MAP);

    public static final Indicator INDICATOR_CORRECT = new Indicator(10, getCurrentDate(), "P/E", 30.1, "$");

    public static final Indicator INDICATOR_WITH_NULL_NAME = new Indicator(10, getCurrentDate(), null, 30.1, "$");
    public static final Indicator INDICATOR_WITH_EMPTY_NAME = new Indicator(10, getCurrentDate(), "", 30.1, "$");

    public static final Indicator INDICATOR_WITH_NULL_VALUE = new Indicator(10, getCurrentDate(), "P/E", null, "$");

    public static final Indicator INDICATOR_WITH_NULL_UNIT = new Indicator(10, getCurrentDate(), "P/E", 30.1, null);

    public static final Indicator INDICATOR_WITH_NULL_COMPANY = new Indicator(10, getCurrentDate(), "P/E", 30.1, "$");

    static
    {
        List<Indicator> indicators = List.of(INDICATOR_CORRECT, INDICATOR_WITH_NULL_NAME, INDICATOR_WITH_EMPTY_NAME, INDICATOR_WITH_NULL_VALUE, INDICATOR_WITH_NULL_UNIT);
        indicators.forEach(indicator -> indicator.setCompany(COMPANY_CORRECT));
    }
}
