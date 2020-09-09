package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.entities.Indicator;
import dmg.stock_screener.repository.IndicatorRepository;
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
public class FinVizInitialDataIndicatorDataService extends AbstractPageParser implements InitialDataIndicator {

    private static final String TEMPLATE_URL = "https://finviz.com/quote.ashx?t=%s";

    private static final String TABLE_PATH = "[class = snapshot-table2]";

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IndicatorRepository indicatorRepository;

    @Autowired
    public FinVizInitialDataIndicatorDataService(IndicatorRepository indicatorRepository) {
        super(TEMPLATE_URL, TABLE_PATH);
        this.indicatorRepository = indicatorRepository;
    }

    @Transactional
    @Override
    public void initializeIndicatorForAllCompanies(List<Company> companies) throws IOException {

        for (Company company : companies) {

            String ticker = company.getTicker();
            parseDataByCustomStep(ticker);

            List<Indicator> indicators = createIndicatorsForCompany();

            for (Indicator indicator : indicators) {
                int company_id = company.getId();
                indicatorRepository.save(indicator, company_id);
            }
            log.info("add indicator for ticker: {}", company.getTicker());
        }
    }

    @Override
    protected void parseDataByCustomStep(String urlParameter) throws IOException {
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

        if ("EPS next Y".equals(name)) {
            if ("%".equals(indicator.getUnit())) {
                indicator.setName("EPS growth next Y");

            } else {
                indicator.setName("EPS estimate next Y");
            }
        }
    }
}
