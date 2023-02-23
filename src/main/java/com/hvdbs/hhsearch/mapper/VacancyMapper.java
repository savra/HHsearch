package com.hvdbs.hhsearch.mapper;

import com.hvdbs.hhsearch.model.dto.VacancyItem;
import com.hvdbs.hhsearch.model.entity.Vacancy;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface VacancyMapper {

    @Mapping(source = "salary.from", target = "salary.lowerBoundary")
    @Mapping(source = "salary.to", target = "salary.upperBoundary")
    Vacancy toEntity(VacancyItem vacancyItem);
    default String mapKeySkill(VacancyItem.KeySkill keySkill) {
        return keySkill.getName();
    }

    @AfterMapping
    default void mapSalary(@MappingTarget Vacancy vacancy) {
        vacancy.getSalary().setVacancy(vacancy);
    }
}
