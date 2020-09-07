package dmg.stock_screener.util;

import dmg.stock_screener.entities.Company;

import java.util.List;

public class CompanyUtil {

    private CompanyUtil() {
    }

    public static boolean isCorrectData(Company company) {
        List<String> fields = List.of(company.getName(), company.getIndustry(), company.getTicker());
        for (String field : fields) {
            if (field == null || field.isEmpty()) return false;
        }
        return true;
    }
}
