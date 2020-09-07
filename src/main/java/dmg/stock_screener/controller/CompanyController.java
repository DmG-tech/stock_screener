package dmg.stock_screener.controller;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepo;
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
public class CompanyController {

    private InitialDataCompany initialDataCompany;

    private InitialDataIndicator initialDataIndicator;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    public CompanyController(InitialDataCompany initialDataCompany, InitialDataIndicator initialDataIndicator) {
        this.initialDataCompany = initialDataCompany;
        this.initialDataIndicator = initialDataIndicator;
    }

    @GetMapping
    public String get() throws IOException {

        //initialDataCompany.initAllCompany();

        List<Company> companies = companyRepo.getAll();
        initialDataIndicator.initializeIndicatorForAllCompanies(companies);
        return "ok";
    }
}
