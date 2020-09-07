package dmg.stock_screener.entities;

import org.junit.jupiter.api.Test;

import static dmg.stock_screener.entities.TestDataCompany.*;

class CompanyTest extends AbstractValidationTest {

    @Test
    public void createWithNullName() {
        validationWithException(COMPANY_WITH_NULL_NAME);
    }

    @Test
    public void createWithEmptyName() {
        validationWithException(COMPANY_WITH_EMPTY_NAME);
    }

    @Test
    public void createWithNullIndustry() {
        validationWithException(COMPANY_WITH_NULL_INDUSTRY);
    }

    @Test
    public void createWithEmptyIndustry() {
        validationWithException(COMPANY_WITH_EMPTY_INDUSTRY);
    }

    @Test
    public void createWithNullTicker() {
        validationWithException(COMPANY_WITH_NULL_TICKER);
    }

    @Test
    public void createWithEmptyTicker() {
        validationWithException(COMPANY_WITH_EMPTY_TICKER);
    }

    @Test
    public void createWithNullIndicators() {
        validation(COMPANY_WITH_NULL_INDICATORS);
    }

    @Test
    public void createCorrect() {
        validation(COMPANY_CORRECT);
    }
}