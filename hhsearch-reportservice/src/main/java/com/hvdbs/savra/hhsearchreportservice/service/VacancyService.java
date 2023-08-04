package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.mapper.VacancyMapper;
import com.hvdbs.savra.hhsearchreportservice.model.dto.CurrencyRs;
import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import com.hvdbs.savra.hhsearchreportservice.repository.VacancyRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacancyService {
    private static final String REPORT_SERVICE = "reportService";
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final RestTemplate restTemplate;
    @Transactional
    public void save(VacancyEvent vacancyEvent) {
        Vacancy newVacancy = vacancyMapper.toEntity(vacancyEvent);
        Vacancy oldVacancy = vacancyRepository.findByUrl(vacancyEvent.getUrl());

        if (oldVacancy != null) {
            newVacancy.setId(oldVacancy.getId());
        }

        vacancyRepository.save(newVacancy);
    }

    public List<Vacancy> findAll() {
        return vacancyRepository.findAll();
    }

    //Отдельно работают, вместе почему-то нет @Bulkhead(name = REPORT_SERVICE, fallbackMethod = "bulkHeadFallback")
    //@RateLimiter(name = REPORT_SERVICE, fallbackMethod = "bulkHeadFallback")
    @Retry(name = REPORT_SERVICE, fallbackMethod = "bulkHeadFallback")
    public CurrencyRs findCurrencies() {
        log.info("findCurrencies was called");

        return restTemplate.getForObject("https://www.cbr-xml-daily.ru/daily_json.js", CurrencyRs.class);
        //return restTemplate.getForObject("http://localhost:8080/order", String.class); Этот сервис в отдельном проекте bulkhead
    }

    public CurrencyRs bulkHeadFallback(Exception e) {
        log.info("bulkHeadFallback was called");
        return new CurrencyRs();
    }
}
