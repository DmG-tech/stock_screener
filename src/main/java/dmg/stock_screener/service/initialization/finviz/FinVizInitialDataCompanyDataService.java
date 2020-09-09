package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.repository.CompanyRepository;
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
public class FinVizInitialDataCompanyDataService extends AbstractPageParser implements InitialDataCompany {

    private static final String TEMPLATE_URL = "https://finviz.com/screener.ashx?r=%s";

    private static final String TABLE_PATH = "#screener-content > table > tbody > tr:nth-of-type(4) > td > table";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CompanyRepository companyRepository;

    @Autowired
    public FinVizInitialDataCompanyDataService(CompanyRepository companyRepository) {
        super(TEMPLATE_URL, TABLE_PATH);
        this.companyRepository = companyRepository;
    }

    @Transactional
    @Override
    public void initializeAllCompanies() throws IOException {
        int startPage = 1;
        int endPage = 7521;
        int stepPage = 20;

        for (int currentPage = startPage; currentPage <= endPage; currentPage += stepPage) {
            parseDataByCustomStep(String.valueOf(currentPage));

            removeRowFromTableWithHeaders();

            for (Element row : getRows()) {
                initializeCellsFromRows(List.of(row));
                Company company = createCompany();
                if (CompanyUtil.isCorrectData(company)) {
                    companyRepository.save(company);
                }
                log.info("create company: {}", company);
            }
        }
    }

    @Override
    protected void parseDataByCustomStep(String urlParameter) throws IOException {
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
