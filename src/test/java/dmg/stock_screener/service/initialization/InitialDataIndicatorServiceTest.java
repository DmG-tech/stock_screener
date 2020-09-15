package dmg.stock_screener.service.initialization;

import dmg.stock_screener.TestMatcher;
import dmg.stock_screener.entities.Indicator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static dmg.stock_screener.TestMatcher.usingFieldsComparator;
import static dmg.stock_screener.service.initialization.TestDataForInitializationService.*;
import static org.junit.jupiter.api.Assertions.*;

class InitialDataIndicatorServiceTest {

    private final InitialDataIndicatorService initialDataIndicatorService = new InitialDataIndicatorService(null, null);

    private static TestMatcher<Indicator> MATCHER = usingFieldsComparator(Indicator.class, "id", "date", "company");

    @Test
    void checkForDuplicationAndRemoveThemWithoutDuplicate() {
        List<Indicator> list = new ArrayList<>(INDICATORS_WITHOUT_DUPLICATE);
        initialDataIndicatorService.checkForDuplicationAndRemoveThem(list);
        assertEquals(INDICATORS_WITHOUT_DUPLICATE, list);
    }

    @Test
    void checkForDuplicationAndRemoveThemAllDuplicate() {
        List<Indicator> list = new ArrayList<>(INDICATORS_ALL_DUPLICATE);
        initialDataIndicatorService.checkForDuplicationAndRemoveThem(list);
        assertEquals(Collections.emptyList(), list);
        assertEquals(0, list.size());
    }

    @Test
    void checkForDuplicationAndRemoveThemWithDuplicate() {
        List<Indicator> list = new ArrayList<>(INDICATORS_WITH_DUPLICATE_1);
        initialDataIndicatorService.checkForDuplicationAndRemoveThem(list);
        MATCHER.assertMatch(INDICATORS_CORRECT_RESULT, list);
        assertEquals(1, list.size());
    }
}