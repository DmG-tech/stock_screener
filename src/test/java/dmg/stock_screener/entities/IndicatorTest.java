package dmg.stock_screener.entities;

import org.junit.jupiter.api.Test;

import static dmg.stock_screener.entities.TestDataIndicator.*;

class IndicatorTest extends AbstractValidationTest{

    @Test
    void createCorrect() {
        validation(INDICATOR_CORRECT);
    }

    @Test
    public void createWithNullName() {
        validationWithException(INDICATOR_WITH_NULL_NAME);
    }

    @Test
    public void createWithEmptyName() {
        validationWithException(INDICATOR_WITH_EMPTY_NAME);
    }

    @Test
    public void createWithNullUnit() {
        validationWithException(INDICATOR_WITH_NULL_UNIT);
    }

    @Test
    public void createWithNullValue() {
        validationWithException(INDICATOR_WITH_NULL_VALUE);
    }

    @Test
    public void createWithNullCompany() {
        validationWithException(INDICATOR_WITH_NULL_COMPANY);
    }
}