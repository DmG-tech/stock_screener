package dmg.stock_screener.repository.jpa;

import dmg.stock_screener.entities.Company;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface JpaCompanyRepo extends JpaRepository<Company, Integer> {

    @Query("DELETE FROM Company c where c.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT c FROM Company c WHERE c.ticker=:ticker")
    Company getByTicker(@Param("ticker") String ticker);

    @EntityGraph(attributePaths = {"indicators"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Company c WHERE c.id =: id")
    Company getWithIndicator(@Param("id") int id);

    @Query("SELECT c FROM Company c WHERE c.industry=:industry")
    List<Company> getByIndustry(@Param("industry") String industry);

    @EntityGraph(attributePaths = {"indicators"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Company c")
    List<Company> getAllWithIndicator();

    @EntityGraph(attributePaths = {"indicators"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Company c WHERE c.industry=:industry")
    List<Company> getByIndustryWithIndicator(@Param("industry") String industry);

}
