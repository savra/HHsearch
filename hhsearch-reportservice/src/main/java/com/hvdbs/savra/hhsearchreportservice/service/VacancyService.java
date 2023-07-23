package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.dto.CurrencyRs;
import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;

import java.util.List;

public interface VacancyService {
    void save(VacancyEvent vacancyEvent);
    List<Vacancy> findAll();
    CurrencyRs findCurrencies();
}
