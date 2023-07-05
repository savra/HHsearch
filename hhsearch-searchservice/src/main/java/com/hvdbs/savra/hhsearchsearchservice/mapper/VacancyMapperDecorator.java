package com.hvdbs.savra.hhsearchsearchservice.mapper;

import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;
import com.hvdbs.savra.hhsearchsearchservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchsearchservice.repository.SalaryRepository;
import com.hvdbs.savra.hhsearchsearchservice.repository.VacancyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDateTime;

public abstract class VacancyMapperDecorator implements VacancyMapper {
    @Autowired
    @Qualifier("delegate")
    private VacancyMapper delegate;

    @Autowired
    VacancyRepository vacancyRepository;

    @Autowired
    SalaryRepository salaryRepository;

    @Override
    public Vacancy toEntity(VacancyItem vacancyItem) {
        Vacancy vacancy = delegate.toEntity(vacancyItem);
        Vacancy v = vacancyRepository.findByVacancyId(vacancy.getVacancyId());

        if (v != null) {
            vacancy.setId(v.getId());
            vacancy.getSalary().setId(v.getSalary().getId());
        }

        vacancy.setLastUpdate(LocalDateTime.now());
        vacancy.getSalary().setVacancy(vacancy);

        return vacancy;
    }
}
