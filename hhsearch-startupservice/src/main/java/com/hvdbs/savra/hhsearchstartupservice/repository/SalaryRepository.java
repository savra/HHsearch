package com.hvdbs.savra.hhsearchstartupservice.repository;

import com.hvdbs.hhsearch.model.entity.Salary;
import org.springframework.data.repository.CrudRepository;

public interface SalaryRepository extends CrudRepository<Salary, Long> {
    Salary findByVacancyId(Long vacancyId);
}
