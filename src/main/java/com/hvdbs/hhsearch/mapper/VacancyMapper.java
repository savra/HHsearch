package com.hvdbs.hhsearch.mapper;

import com.hvdbs.hhsearch.model.dto.VacancyItem;
import com.hvdbs.hhsearch.model.entity.Vacancy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface VacancyMapper {
    Vacancy toEntity(VacancyItem vacancyItem);
    default String mapKeySkill(VacancyItem.KeySkill keySkill) {
        return keySkill.getName();
    }
}
