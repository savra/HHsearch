package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.mapper.VacancyMapper;
import com.hvdbs.savra.hhsearchreportservice.model.dto.CurrencyRs;
import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import com.hvdbs.savra.hhsearchreportservice.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final RestTemplate restTemplate;
    @Transactional
    @Override
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

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, maxDelay = 5000, multiplier = 0.7, random = true))
    @Override
    public CurrencyRs findCurrencies() {
        log.info("findCurrencies was called");

        return restTemplate.getForObject("https://www.cbr-xml-daily.ru/daily_json.js", CurrencyRs.class);
    }

    @Recover
    public CurrencyRs recoverFindCurrencies() {
        log.info("recoverFindCurrencies was called");
        return new CurrencyRs();
    }
}
