package dmg.stock_screener.util;

import java.time.LocalDate;

public class DateUtil {

    private DateUtil() {
    }

    public static LocalDate getCurrentDate() {
        LocalDate now = LocalDate.now();
        return LocalDate.of(now.getYear(), now.getMonth(), 1);
    }
}
