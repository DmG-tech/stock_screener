package dmg.stock_screener.util;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> void checkNull(T object, String msg) {
        if (object == null) throw new IllegalArgumentException("Object must be not null: " + msg);
    }
}
