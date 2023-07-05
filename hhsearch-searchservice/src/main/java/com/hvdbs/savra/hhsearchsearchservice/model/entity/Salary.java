package com.hvdbs.savra.hhsearchsearchservice.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "salary")
public class Salary {
    @Id
    @GeneratedValue(generator = "salary_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "salary_seq", name = "salary_seq", allocationSize = 1)
    private Long id;

    private BigDecimal lowerBoundary;

    private BigDecimal upperBoundary;

    private String currency;

    private Boolean gross;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary salary = (Salary) o;
        return Objects.equals(id, salary.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
