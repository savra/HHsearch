package com.hvdbs.hhsearch.model.entity;

import com.hvdbs.hhsearch.model.enums.Employment;
import com.hvdbs.hhsearch.model.enums.Experience;
import com.hvdbs.hhsearch.model.enums.Schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Table(name = "vacancy")
@Entity
public class Vacancy {
    @Id
    @GeneratedValue(generator = "vacancy_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "vacancy_seq", name = "vacancy_seq", allocationSize = 1)
    private Long id;
    private String vacancyId;
    private String name;
    private boolean premium;
    private String description;
    @CollectionTable(name = "key_skill", joinColumns = @JoinColumn(name = "vacancy_id"))
    @Column(name = "name")
    @ElementCollection
    private Set<String> keySkills = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private Schedule schedule;
    private boolean acceptHandicapped;
    private boolean acceptKids;
    @Enumerated(EnumType.STRING)
    private Experience experience;
    @NotNull
    private String alternateUrl;
    @NotNull
    private String applyAlternateUrl;
    private String code;
    @Enumerated(EnumType.STRING)
    private Employment employment;

    @OneToOne(mappedBy = "vacancy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Salary salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equals(id, vacancy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
