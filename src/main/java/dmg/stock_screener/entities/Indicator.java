package dmg.stock_screener.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "indicators", uniqueConstraints = {@UniqueConstraint(columnNames = {"company_id", "name"}, name = "indicator_unique_company_name_idx")})
public class Indicator extends AbstractNamedEntity {

    @Column(name = "value")
    private Double value;

    @Column(name = "unit")
    private String unit;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Company company;

    public Indicator() {
    }

    public Indicator(LocalDate date, String name, Double value, String unit) {
        this(null, date, name, value, unit);
    }

    public Indicator(Integer id, LocalDate date, String name, Double value, String unit) {
        super(id, name);
        this.date = date;
        this.value = value;
        this.unit = unit;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "id=" + id + '\'' +
                ", name='" + name + '\'' +
                ", value=" + value + '\'' +
                ", unit='" + unit + '\'' +
                ", date=" + date + '\'' +
                ", company=" + company + '\'' +
                '}';
    }
}
