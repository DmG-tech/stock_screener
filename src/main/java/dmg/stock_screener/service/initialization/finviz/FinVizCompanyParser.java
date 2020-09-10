package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.service.initialization.CompanyParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static dmg.stock_screener.util.ValidationUtil.checkNull;

@Component
public class FinVizCompanyParser extends AbstractPageParser implements CompanyParser {

    private static final String TEMPLATE_URL = "https://finviz.com/screener.ashx?r=%s";

    private static final String TABLE_PATH = "#screener-content > table > tbody > tr:nth-of-type(4) > td > table";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public FinVizCompanyParser() {
        super(TEMPLATE_URL, TABLE_PATH);
    }


    @Override
    public List<Company> parseAllCompanies() throws IOException {
        int startPage = 1;
        int endPage = 7521;
        int stepPage = 20;

        List<Company> companies = new LinkedList<>();
        for (int currentPage = startPage; currentPage <= endPage; currentPage += stepPage) {
            parseDataByCustomStep(String.valueOf(currentPage));

            removeRowFromTableWithHeaders();

            for (Element row : getRows()) {
                initializeCellsFromRows(List.of(row));
                Company company = createCompany();
                companies.add(company);
                log.info("parse company : {}", company.getTicker());
            }
        }
        return companies;
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
