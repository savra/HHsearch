package com.hvdbs.savra.hhsearchreportservice.mapper;

import com.hvdbs.savra.hhsearchreportservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchreportservice.model.event.VacancyEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VacancyMapper {
    Vacancy toEntity(VacancyEvent vacancyEvent);
}