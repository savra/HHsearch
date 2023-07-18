package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.model.dto.VacancyDto;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;

import java.util.List;

public interface VacancyService {
    void save(VacancyEvent vacancyEvent);
    List<VacancyDto> findAll();
}
