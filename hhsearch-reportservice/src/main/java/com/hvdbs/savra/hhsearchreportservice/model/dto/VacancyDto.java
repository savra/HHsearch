package com.hvdbs.savra.hhsearchreportservice.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
public class VacancyDto {
    private String name;
    private String experience;
    private String url;
    private BigDecimal lowerBoundarySalary;
    private BigDecimal upperBoundarySalary;
    private String keySkills;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof VacancyDto vacancy)) {
            return false;
        }

        return getUrl().equals(vacancy.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl());
    }
}
