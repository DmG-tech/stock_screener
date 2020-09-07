package dmg.stock_screener.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "companies")
public class Company extends AbstractNamedEntity {

    @NotBlank
    @Column(name = "industry", nullable = false)
    private String industry;

    @NotBlank
    @Column(name = "ticker", nullable = false)
    private String ticker;

    @NotNull
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
    private Map<String, Indicator> indicators;

    public Company() {
    }

    public Company(String name, String industry, String ticker) {
        this(null, name, industry, ticker);
    }

    public Company(Integer id, String name, String industry, String ticker) {
        this(id, name, industry, ticker, null);
    }

    public Company(String name, String industry, String ticker, Map<String, Indicator> indicators) {
        this(null, name, industry, ticker, indicators);
    }

    public Company(Integer id, String name, String industry, String ticker, Map<String, Indicator> indicators) {
        super(id, name);
        this.industry = industry;
        this.ticker = ticker;
        setIndicators(indicators);
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Map<String, Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(Map<String, Indicator> indicators) {
        this.indicators = indicators == null ? Collections.EMPTY_MAP : indicators;
    }

    public void setIndicators(Collection<Indicator> indicators) {
        if (indicators == null) {
            this.indicators = Collections.EMPTY_MAP;
        }
        else {
            Map<String, Indicator> map = new HashMap<>();
            indicators.forEach(indicator -> map.putIfAbsent(indicator.getName(), indicator));
            this.indicators = map;
        }
    }

    @Override
    public String toString() {
        return "Company{" +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                "industry='" + industry + '\'' +
                ", ticker='" + ticker + '\'' +
                '}';
    }
}
