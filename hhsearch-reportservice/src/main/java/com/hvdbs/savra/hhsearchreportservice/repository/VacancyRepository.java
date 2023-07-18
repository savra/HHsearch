package com.hvdbs.savra.hhsearchreportservice.repository;

import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    Vacancy findByUrl(String url);
}
