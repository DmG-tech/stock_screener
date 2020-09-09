package dmg.stock_screener.repository.jpa;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataJpaCompanyRepository implements CompanyRepository {

    private JpaCompanyRepository companyRepository;

    @Autowired
    public DataJpaCompanyRepository(JpaCompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public boolean delete(int id) {
        return companyRepository.delete(id) != 0;
    }

    @Override
    public Company get(int id) {
        return companyRepository.getOne(id);
    }

    @Override
    public Company getByTicker(String ticker) {
        return companyRepository.getByTicker(ticker);
    }

    @Override
    public Company getWithIndicator(int id) {
        return companyRepository.getWithIndicator(id);
    }

    @Override
    public List<Company> getByIndustry(String industry) {
        return sorted(companyRepository.getByIndustry(industry));
    }

    @Override
    public List<Company> getAll() {
        return sorted(companyRepository.findAll());
    }

    @Override
    public List<Company> getAllWithIndicator() {
        return sorted(companyRepository.getAllWithIndicator());
    }

    @Override
    public List<Company> getByIndustryWithIndicator(String industry) {
        return sorted(companyRepository.getByIndustryWithIndicator(industry));
    }

    private List<Company> sorted(List<Company> companies) {
        return companies
                .stream()
                .sorted(Comparator.comparing(Company::getName))
                .collect(Collectors.toList());
    }
}
