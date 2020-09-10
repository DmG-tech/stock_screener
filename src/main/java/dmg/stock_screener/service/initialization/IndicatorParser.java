package dmg.stock_screener.service.initialization;

import dmg.stock_screener.entities.Indicator;

import java.io.IOException;
import java.util.List;

public interface IndicatorParser {
    List<Indicator> parseIndicatorForCompany(String ticker) throws IOException;
}
