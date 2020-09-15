package dmg.stock_screener.service.initialization;

import dmg.stock_screener.entities.Indicator;

import java.util.List;

public class TestDataForInitializationService {

    public static final List<Indicator> INDICATORS_WITHOUT_DUPLICATE = List.of(new Indicator(null, null, "P", null, null),
            new Indicator(null, null, "Q", null, null),
            new Indicator(null, null, "W", null, null));

    public static final List<Indicator> INDICATORS_ALL_DUPLICATE = List.of(new Indicator(null, null, "P", null, null),
            new Indicator(null, null, "P", null, null),
            new Indicator(null, null, "P", null, null));

    public static final List<Indicator> INDICATORS_WITH_DUPLICATE_1 = List.of(new Indicator(null, null, "P", null, null),
            new Indicator(null, null, "Q", null, null),
            new Indicator(null, null, "P", null, null));

    public static final List<Indicator> INDICATORS_CORRECT_RESULT = List.of(new Indicator(null, null, "Q", null, null));
}
