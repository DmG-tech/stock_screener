package dmg.stock_screener.repository;

import dmg.stock_screener.entities.Indicator;

public interface IndicatorRepo {

    Indicator save(Indicator indicator, int com_id);

    boolean delete(int id, int com_id);

    Indicator get(int id, int com_id);
}
