package dmg.stock_screener.service.initialization;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepository;
import dmg.stock_screener.util.CompanyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class InitialDataCompanyService implements InitialDataCompany {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CompanyRepository companyRepository;

    private final CompanyParser companyParser;

    @Autowired
    public InitialDataCompanyService(CompanyRepository companyRepository, CompanyParser companyParser) {
        this.companyRepository = companyRepository;
        this.companyParser = companyParser;
    }

    @Transactional
    @Override
    public void initializeAllCompanies() throws IOException {
        List<Company> companies = companyParser.parseAllCompanies();

        for (Company company : companies) {
            if (CompanyUtil.isCorrectData(company)) {
                companyRepository.save(company);
                log.info("create company: {}", company);
            }
            else {
                log.warn("company in not corrected: {}", company);
            }
        }
    }

}
