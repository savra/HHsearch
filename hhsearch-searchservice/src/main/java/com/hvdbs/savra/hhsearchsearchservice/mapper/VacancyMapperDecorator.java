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

        if (vacancy != null) {
            Vacancy currentVacancy = vacancyRepository.findByVacancyId(vacancy.getVacancyId());

            if (currentVacancy != null) {
                vacancy.setId(currentVacancy.getId());

                if (vacancy.getSalary() != null && currentVacancy.getSalary() != null) {
                    vacancy.getSalary().setId(currentVacancy.getSalary().getId());
                }
            }

            vacancy.setLastUpdate(LocalDateTime.now());

            if (vacancy.getSalary() != null) {
                vacancy.getSalary().setVacancy(vacancy);
            }
        }

        return vacancy;
    }
}
