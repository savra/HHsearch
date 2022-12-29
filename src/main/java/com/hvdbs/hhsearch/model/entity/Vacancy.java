package com.hvdbs.hhsearch.model.entity;

import jakarta.persistence.*;

@Table(name = "vacancy")
@Entity
public class Vacancy {
    @Id
    @GeneratedValue(generator = "vacancy_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "vacancy_seq", name = "vacancy_seq", allocationSize = 1)
    private Long id;


}
