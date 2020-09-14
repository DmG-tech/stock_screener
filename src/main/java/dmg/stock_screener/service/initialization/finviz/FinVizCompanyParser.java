package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.service.initialization.CompanyParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static dmg.stock_screener.util.ValidationUtil.checkNull;

@Component
@PropertySource("classpath:config/fin_viz_config.properties")
public class FinVizCompanyParser extends AbstractPageParser implements CompanyParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${start_page}")
    private int startPage;

    @Value("${end_page}")
    private int endPage;

    @Value("${step_page}")
    private int stepPage;

    @Autowired
    public FinVizCompanyParser(@Value("${template_url_for_company}") String templateUrl,  @Value("${path_to_table_for_company}") String tablePath) {
        super(templateUrl, tablePath);
    }

    @Override
    public List<Company> parseAllCompanies() throws IOException {

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
