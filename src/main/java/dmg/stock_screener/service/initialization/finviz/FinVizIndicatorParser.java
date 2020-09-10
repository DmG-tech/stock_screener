package dmg.stock_screener.service.initialization.finviz;

import dmg.stock_screener.entities.Indicator;
import dmg.stock_screener.service.initialization.IndicatorParser;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static dmg.stock_screener.util.DateUtil.getCurrentDate;
import static dmg.stock_screener.util.IndicatorUtil.setValueAndUnit;

@Component
public class FinVizIndicatorParser extends AbstractPageParser implements IndicatorParser {

    private static final String TEMPLATE_URL = "https://finviz.com/quote.ashx?t=%s";

    private static final String TABLE_PATH = "[class = snapshot-table2]";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public FinVizIndicatorParser() {
        super(TEMPLATE_URL, TABLE_PATH);
    }

    @Override
    public List<Indicator> parseIndicatorForCompany(String ticker) throws IOException {
        parseDataByCustomStep(ticker);
        List<Indicator> indicators = new LinkedList<>();

        List<Element> cells = getCells();
        for (int i = 0; i < cells.size(); i += 2) {

            String name = cells.get(i).text();
            String value = cells.get(i + 1).text();

            Indicator indicator = new Indicator();
            initializeIndicatorFields(indicator, name, value);
        }
        log.info("parse indicators for ticker: {}", ticker);
        return indicators;
    }

    @Override
    protected void parseDataByCustomStep(String urlParameter) throws IOException {
        initializePage(urlParameter);
        initializeTableFromPage();
        initializeRowsFromTable();
        initializeCellsFromRows(getRows());
    }

    private void initializeIndicatorFields(Indicator indicator, String name, String value) {
        indicator.setName(name);
        indicator.setDate(getCurrentDate());
        setValueAndUnit(indicator, value);
    }
}
