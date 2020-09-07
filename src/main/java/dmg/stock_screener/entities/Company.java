package dmg.stock_screener.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

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
    private Set<Indicator> indicators;

    public Company() {
    }

    public Company(String name, String industry, String ticker) {
        this(null, name, industry, ticker);
    }

    public Company(Integer id, String name, String industry, String ticker) {
        this(id, name, industry, ticker, null);
    }

    public Company(String name, String industry, String ticker, Set<Indicator> indicators) {
        this(null, name, industry, ticker, indicators);
    }

    public Company(Integer id, String name, String industry, String ticker, Set<Indicator> indicators) {
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

    public Set<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(Set<Indicator> indicators) {
        this.indicators = indicators == null ? Collections.EMPTY_SET : Collections.EMPTY_SET;
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
