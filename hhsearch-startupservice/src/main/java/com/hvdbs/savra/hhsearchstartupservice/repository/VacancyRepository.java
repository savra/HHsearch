package com.hvdbs.savra.hhsearchstartupservice.repository;

import com.hvdbs.savra.hhsearchstartupservice.model.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    Vacancy findByVacancyId(String vacancyId);
}
