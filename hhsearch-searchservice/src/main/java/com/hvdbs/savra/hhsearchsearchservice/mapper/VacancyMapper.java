package com.hvdbs.savra.hhsearchsearchservice.mapper;

import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;
import com.hvdbs.savra.hhsearchsearchservice.model.entity.Vacancy;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@DecoratedWith(VacancyMapperDecorator.class)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VacancyMapper {
    @Mapping(source = "salary.from", target = "salary.lowerBoundary")
    @Mapping(source = "salary.to", target = "salary.upperBoundary")
    Vacancy toEntity(VacancyItem vacancyItem);
    default String mapKeySkill(VacancyItem.KeySkill keySkill) {
        return keySkill.getName();
    }
}
