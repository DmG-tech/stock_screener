package dmg.stock_screener.service.initialization.finviz;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static dmg.stock_screener.util.ValidationUtil.*;

public abstract class AbstractPageParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final String templateUrl;

    private final String tablePath;

    private Document page;

    private Element table;

    private List<Element> rows;

    private List<Element> cells;

    public AbstractPageParser(String templateUrl, String tablePath) {
        this.templateUrl = templateUrl;
        this.tablePath = tablePath;
    }

    protected Document getPage() {
        return page;
    }

    protected Element getTable() {
        return table;
    }

    protected List<Element> getRows() {
        return rows;
    }

    protected List<Element> getCells() {
        return cells;
    }

    protected abstract void parseDataByCustomStep(String urlParameter) throws IOException;

    protected void initializePage(String urlParameter) throws IOException {
        String url = String.format(templateUrl, urlParameter);
        log.info("set current url to: {}", url);
        Connection connection = Jsoup.connect(url);
        page = connection.get();
    }

    protected void initializeTableFromPage() throws IllegalArgumentException {
        checkNull(page, "page of company list");
        table = page.selectFirst(tablePath);
    }

    protected void initializeRowsFromTable() throws IllegalArgumentException {
        checkNull(table, "table of company list");
        String tag = "tr";
        rows = table.getElementsByTag(tag);
    }

    protected void initializeCellsFromRows(List<Element> rows) {
        checkNull(rows, "row  of company list");
        List<Element> cells = new LinkedList<>();

        for (Element row : rows) {
            cells.addAll(row.getElementsByTag("td"));
        }
        this.cells = cells;
    }

}
