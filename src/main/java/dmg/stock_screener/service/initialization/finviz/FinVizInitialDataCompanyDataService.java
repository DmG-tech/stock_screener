package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepo;
import dmg.stock_screener.service.initialization.InitialDataCompany;
import dmg.stock_screener.util.CompanyUtil;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static dmg.stock_screener.util.ValidationUtil.checkNull;

@Component
public class FinVizInitialDataCompanyDataService extends AbstractFinVizDataService implements InitialDataCompany {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CompanyRepo companyRepo;

    @Autowired
    public FinVizInitialDataCompanyDataService(CompanyRepo companyRepo) {
        this.companyRepo = companyRepo;

        setTemplateUrl("https://finviz.com/screener.ashx?r=%s");
        setTablePath("#screener-content > table > tbody > tr:nth-of-type(4) > td > table");
    }

    @Transactional
    @Override
    public void initializeAllCompanies() throws IOException {
        int startPageNumber = 1;
        int endPageNumber = 7521;
        int stepPageNumber = 20;

        for (int currentPageNumber = startPageNumber; currentPageNumber <= endPageNumber; currentPageNumber += stepPageNumber) {
            initializeDataForParse(String.valueOf(currentPageNumber));

            removeRowFromTableWithHeaders();

            for (Element row : getRows()) {
                initializeCellsFromRows(List.of(row));
                Company company = createCompany();
                if (CompanyUtil.isCorrectData(company)) {
                    companyRepo.save(company);
                }
                log.info("create company: {}", company);
            }
        }
    }

    @Override
    protected void initializeDataForParse(String urlParameter) throws IOException {
        initializePage(String.valueOf(urlParameter));
        initializeTableFromPage();
        initializeRowsFromTable();
    }

    private void removeRowFromTableWithHeaders() {
        getRows().remove(0);
    }

    private Company createCompany() throws IllegalArgumentException {
        List<Element> cells = getCells();
        checkNull(cells, "cells  of company list");

        String ticker = cells.get(1).text();
        String name = cells.get(2).text();
        String industry = cells.get(3).text();

        return new Company(name, industry, ticker);
    }
}
