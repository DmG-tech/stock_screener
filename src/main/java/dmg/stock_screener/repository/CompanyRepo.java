package dmg.stock_screener.repository;

import dmg.stock_screener.entities.Company;

import java.util.List;

public interface CompanyRepo {

    Company save(Company company);

    boolean delete(int id);

    Company get(int id);

    Company getByTicker(String ticker);

    Company getWithIndicator(int id);

    List<Company> getByIndustry(String industry);

    List<Company> getAll();

    List<Company> getAllWithIndicator();

}
