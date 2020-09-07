package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.entities.Indicator;
import dmg.stock_screener.repository.IndicatorRepo;
import dmg.stock_screener.service.initialization.InitialDataIndicator;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static dmg.stock_screener.util.DateUtil.getCurrentDate;
import static dmg.stock_screener.util.IndicatorUtil.setValueAndUnit;
import static dmg.stock_screener.util.IndicatorUtil.isCorrectData;

@Component
public class FinVizInitialDataIndicatorDataService extends AbstractFinVizDataService implements InitialDataIndicator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IndicatorRepo indicatorRepo;

    @Autowired
    public FinVizInitialDataIndicatorDataService(IndicatorRepo indicatorRepo) {
        this.indicatorRepo = indicatorRepo;

        setTemplateUrl("https://finviz.com/quote.ashx?t=%s");
        setTablePath("[class = snapshot-table2]");
    }

    @Transactional
    @Override
    public void initializeIndicatorForAllCompanies(List<Company> companies) throws IOException {

        for (Company company : companies) {

            initializeDataForParse(company.getTicker());

            List<Indicator> indicators = createIndicatorsForCompany();

            for (Indicator indicator : indicators) {
                indicatorRepo.save(indicator, company.getId());
            }
            log.info("add indicator for ticker: {}", company.getTicker());
        }
    }

    @Override
    protected void initializeDataForParse(String urlParameter) throws IOException {
        initializePage(urlParameter);
        initializeTableFromPage();
        initializeRowsFromTable();
        initializeCellsFromRows(getRows());
    }

    @Override
    public void initializeIndicatorForCompany(Company company) throws IOException {
        initializeIndicatorForAllCompanies(List.of(company));
    }

    private List<Indicator> createIndicatorsForCompany() {
        List<Indicator> indicators = new LinkedList<>();

        List<Element> cells = getCells();
        for (int i = 0; i < cells.size(); i += 2) {

            String name = cells.get(i).text();
            String value = cells.get(i + 1).text();

            Indicator indicator = new Indicator();
            initializeIndicatorFields(indicator, name, value);

            if (isCorrectData(indicator)) {
                indicators.add(indicator);
                log.info("indicator created: {}", indicator.toString());
            }
        }
        return indicators;
    }

    private void initializeIndicatorFields(Indicator indicator, String name, String value) {
        indicator.setName(name);
        indicator.setDate(getCurrentDate());
        setValueAndUnit(indicator, value);
    }
}
