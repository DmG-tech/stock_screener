package dmg.stock_screener.controller;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepository;
import dmg.stock_screener.service.initialization.InitialDataCompany;
import dmg.stock_screener.service.initialization.InitialDataIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = {"/", "index"})
public class InitialDataController {

    private final InitialDataCompany initialDataCompany;
    private final InitialDataIndicator initialDataIndicator;
    private final CompanyRepository companyRepository;

    @Autowired
    public InitialDataController(InitialDataCompany initialDataCompany, InitialDataIndicator initialDataIndicator, CompanyRepository companyRepository) {
        this.initialDataCompany = initialDataCompany;
        this.initialDataIndicator = initialDataIndicator;
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public String get() throws IOException {
        initialDataCompany.initializeAllCompanies();

        List<Company> companies = companyRepository.getAll();
        initialDataIndicator.initializeIndicatorForAllCompanies(companies);
        return "ok";
    }
}
