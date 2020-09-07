package dmg.stock_screener.service.initialization;

import dmg.stock_screener.entities.Company;

import java.io.IOException;
import java.util.List;

public interface InitialDataIndicator {

    void initializeIndicatorForAllCompanies(List<Company> companies) throws IOException;

    void initializeIndicatorForCompany(Company company) throws IOException;

}
