package dmg.stock_screener.repository.jpa;

import dmg.stock_screener.entities.Indicator;
import dmg.stock_screener.repository.IndicatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaIndicatorRepository implements IndicatorRepository {

    private JpaIndicatorRepository indicatorRepository;

    private JpaCompanyRepository companyRepository;

    @Autowired
    public DataJpaIndicatorRepository(JpaIndicatorRepository indicatorRepository, JpaCompanyRepository companyRepository) {
        this.indicatorRepository = indicatorRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Indicator save(Indicator indicator, int com_id) {
        if (!indicator.isNew() && get(indicator.getId(), com_id) == null) {
            return null;
        }
        indicator.setCompany(companyRepository.getOne(com_id));
        return indicatorRepository.save(indicator);
    }

    @Override
    public boolean delete(int id, int com_id) {
        return indicatorRepository.delete(id, com_id) != 0;
    }

    @Override
    public Indicator get(int id, int com_id) {
        return indicatorRepository.get(id, com_id);
    }
}
