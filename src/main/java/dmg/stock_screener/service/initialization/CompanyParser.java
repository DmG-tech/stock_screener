package dmg.stock_screener.service.initialization;

import dmg.stock_screener.entities.Company;

import java.io.IOException;
import java.util.List;

public interface CompanyParser {
    List<Company> parseAllCompanies() throws IOException;
}
