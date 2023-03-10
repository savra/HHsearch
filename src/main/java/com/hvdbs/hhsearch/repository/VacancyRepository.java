package com.hvdbs.hhsearch.repository;

import com.hvdbs.hhsearch.model.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    Vacancy findByVacancyId(String vacancyId);
}
