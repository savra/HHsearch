package com.hvdbs.savra.hhsearchreportservice.repository;

import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;

public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
    Vacancy findByUrl(String url);
}
