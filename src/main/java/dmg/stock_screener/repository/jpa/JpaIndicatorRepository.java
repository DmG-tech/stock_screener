package dmg.stock_screener.repository.jpa;

import dmg.stock_screener.entities.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface JpaIndicatorRepository extends JpaRepository<Indicator, Integer> {

    @Query("DELETE FROM Indicator i WHERE i.id=:id AND i.company.id=:com_id")
    int delete(@Param("id") int id, @Param("com_id") int com_id);

    @Query("SELECT i FROM Indicator i WHERE i.id=:id AND i.company.id=:com_id")
    Indicator get(@Param("id") int id, @Param("com_id") int com_id);
}
