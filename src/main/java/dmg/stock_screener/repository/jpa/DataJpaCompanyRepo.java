package dmg.stock_screener.repository.jpa;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaCompanyRepo implements CompanyRepo {

    private JpaCompanyRepo companyRepo;

    @Autowired
    public DataJpaCompanyRepo(JpaCompanyRepo companyRepo) {
        this.companyRepo = companyRepo;
    }

    @Override
    public Company save(Company company) {
        return companyRepo.save(company);
    }

    @Override
    public boolean delete(int id) {
        return companyRepo.delete(id) != 0;
    }

    @Override
    public Company get(int id) {
        return companyRepo.getOne(id);
    }

    @Override
    public Company getByTicker(String ticker) {
        return companyRepo.getByTicker(ticker);
    }

    @Override
    public Company getWithIndicator(int id) {
        return companyRepo.getWithIndicator(id);
    }

    @Override
    public List<Company> getByIndustry(String industry) {
        return sorted(companyRepo.getByIndustry(industry));
    }

    @Override
    public List<Company> getAll() {
        return sorted(companyRepo.findAll());
    }

    @Override
    public List<Company> getAllWithIndicator() {
        return sorted(companyRepo.getAllWithIndicator());
    }

    @Override
    public List<Company> getByIndustryWithIndicator(String industry) {
        return sorted(companyRepo.getByIndustryWithIndicator(industry));
    }

    private List<Company> sorted(List<Company> companies) {
        return companies
                .stream()
                .sorted(Comparator.comparing(Company::getName))
                .collect(Collectors.toList());
    }
}
