package com.hvdbs.savra.hhsearchreportservice.service;

import com.hvdbs.savra.hhsearchreportservice.mapper.VacancyMapper;
import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import com.hvdbs.savra.hhsearchreportservice.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
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
}
