package dmg.stock_screener.service.initialization;

import dmg.stock_screener.entities.Company;
import dmg.stock_screener.entities.Indicator;
import dmg.stock_screener.repository.IndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static dmg.stock_screener.util.IndicatorUtil.isCorrectData;

@Service
public class InitialDataIndicatorService implements InitialDataIndicator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final IndicatorRepository indicatorRepository;

    private final IndicatorParser indicatorParser;

    @Autowired
    public InitialDataIndicatorService(IndicatorRepository indicatorRepository, IndicatorParser indicatorParser) {
        this.indicatorRepository = indicatorRepository;
        this.indicatorParser = indicatorParser;
    }

    @Transactional
    @Override
    public void initializeIndicatorForAllCompanies(List<Company> companies) throws IOException {

        for (Company company : companies) {

            String ticker = company.getTicker();

            List<Indicator> indicators = indicatorParser.parseIndicatorForCompany(ticker);

            checkForDuplicationAndRemoveThem(indicators);

            for (Indicator indicator : indicators) {
                int company_id = company.getId();
                if (isCorrectData(indicator)) {
                    indicatorRepository.save(indicator, company_id);
                }
            }
            log.info("add indicators for ticker: {}", company.getTicker());
        }
    }

    @Override
    public void initializeIndicatorForCompany(Company company) throws IOException {
        initializeIndicatorForAllCompanies(List.of(company));
    }

    private void checkForDuplicationAndRemoveThem(List<Indicator> indicators) {
        Set<Indicator> duplicates = new LinkedHashSet<>();

        for (int i = 0; i < indicators.size(); i++) {
            Indicator currentIndicator = indicators.get(i);
            List<Indicator> subList = indicators.subList(i + 1, indicators.size() - 1);

            duplicates.addAll(getDuplicates(currentIndicator, subList));
        }
        writeToLogDuplicatesIfFound(duplicates);
        indicators.removeAll(duplicates);
    }

    private Collection<Indicator> getDuplicates(Indicator currentIndicator, Collection<Indicator> checkedSubList) {
        int counterDuplication = 0;

        Set<Indicator> duplicates = new LinkedHashSet<>();
        for (Indicator indicator : checkedSubList) {
            if (currentIndicator.getName().equals(indicator.getName())) {
                counterDuplication++;
                duplicates.add(indicator);
            }
        }
        if (counterDuplication > 0) duplicates.add(currentIndicator);
        return duplicates;
    }

    private void writeToLogDuplicatesIfFound(Collection<Indicator> duplicates) {
        if (duplicates.size() > 0) {
            log.warn("found duplicates in the amount of : {}", duplicates.size());
            for (Indicator indicator : duplicates) {
                log.warn("duplicate : {}", indicator);
            }
        }
    }
}
