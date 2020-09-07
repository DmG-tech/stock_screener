package dmg.stock_screener.entities;

import java.util.Collections;

public class TestDataCompany {

    public static final Company COMPANY_CORRECT = new Company(1, "Corporation of Evil", "IT", "COE", Collections.EMPTY_MAP);

    public static final Company COMPANY_WITH_NULL_NAME = new Company(1, null, "IT", "COE", Collections.EMPTY_MAP);
    public static final Company COMPANY_WITH_EMPTY_NAME = new Company(1, "", "IT", "COE", Collections.EMPTY_MAP);

    public static final Company COMPANY_WITH_NULL_INDUSTRY = new Company(1, "Corporation of Evil", null, "COE", Collections.EMPTY_MAP);
    public static final Company COMPANY_WITH_EMPTY_INDUSTRY = new Company(1, "Corporation of Evil", "", "COE", Collections.EMPTY_MAP);

    public static final Company COMPANY_WITH_NULL_TICKER = new Company(1, "Corporation of Evil", "IT", null, Collections.EMPTY_MAP);
    public static final Company COMPANY_WITH_EMPTY_TICKER = new Company(1, "Corporation of Evil", "IT", "", Collections.EMPTY_MAP);

    public static final Company COMPANY_WITH_NULL_INDICATORS = new Company(1, "Corporation of Evil", "IT", "COE", null);
}
