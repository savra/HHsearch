package com.hvdbs.savra.hhsearchsearchservice.repository;

import com.hvdbs.savra.hhsearchsearchservice.model.entity.Salary;
import org.springframework.data.repository.CrudRepository;

public interface SalaryRepository extends CrudRepository<Salary, Long> {
    Salary findByVacancyId(Long vacancyId);
}
