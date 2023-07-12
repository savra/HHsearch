package com.hvdbs.savra.hhsearchreportservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "vacancy")
public class Vacancy {
    @Id
    @GeneratedValue(generator = "vacancy_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "vacancy_seq", sequenceName = "vacancy_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String experience;
    private String url;
    private BigDecimal lowerBoundarySalary;
    private BigDecimal upperBoundarySalary;
    private String keySkill;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Vacancy vacancy)) {
            return false;
        }

        return getUrl().equals(vacancy.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl());
    }
}
