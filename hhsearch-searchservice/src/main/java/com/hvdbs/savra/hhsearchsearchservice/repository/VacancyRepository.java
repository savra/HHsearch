package com.hvdbs.savra.hhsearchsearchservice.repository;

import com.hvdbs.savra.hhsearchsearchservice.model.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    Vacancy findByVacancyId(String vacancyId);
}
