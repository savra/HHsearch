package com.hvdbs.savra.hhsearchreportservice.mapper;

import com.hvdbs.savra.hhsearchreportservice.model.dto.VacancyDto;
import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VacancyMapper {
    Vacancy toEntity(VacancyEvent vacancyEvent);
    VacancyDto toDto(Vacancy vacancy);
    List<VacancyDto> toDto(List<Vacancy> vacancy);
}