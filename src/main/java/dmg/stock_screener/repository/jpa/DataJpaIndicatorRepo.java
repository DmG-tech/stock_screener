package dmg.stock_screener.repository.jpa;

import dmg.stock_screener.entities.Indicator;
import dmg.stock_screener.repository.IndicatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaIndicatorRepo implements IndicatorRepo {

    private JpaIndicatorRepo indicatorRepo;

    private JpaCompanyRepo companyRepo;

    @Autowired
    public DataJpaIndicatorRepo(JpaIndicatorRepo indicatorRepo, JpaCompanyRepo companyRepo) {
        this.indicatorRepo = indicatorRepo;
        this.companyRepo = companyRepo;
    }

    @Override
    public Indicator save(Indicator indicator, int com_id) {
        if (!indicator.isNew() && get(indicator.getId(), com_id) == null) {
            return null;
        }
        indicator.setCompany(companyRepo.getOne(com_id));
        return indicatorRepo.save(indicator);
    }

    @Override
    public boolean delete(int id, int com_id) {
        return indicatorRepo.delete(id, com_id) != 0;
    }

    @Override
    public Indicator get(int id, int com_id) {
        return indicatorRepo.get(id, com_id);
    }
}
