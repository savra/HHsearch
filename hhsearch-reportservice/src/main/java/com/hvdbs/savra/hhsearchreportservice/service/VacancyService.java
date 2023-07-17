package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;

public interface VacancyService {
    void save(VacancyEvent vacancyEvent);
}
