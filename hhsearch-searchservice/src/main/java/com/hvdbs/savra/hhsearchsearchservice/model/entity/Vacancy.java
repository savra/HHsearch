package com.hvdbs.savra.hhsearchsearchservice.model.entity;

import com.hvdbs.savra.hhsearchsearchservice.model.enums.Employment;
import com.hvdbs.savra.hhsearchsearchservice.model.enums.Experience;
import com.hvdbs.savra.hhsearchsearchservice.model.enums.Schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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

    private LocalDateTime lastUpdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Vacancy vacancy)) {
            return false;
        }

        return Objects.equals(id, vacancy.id) && vacancyId.equals(vacancy.vacancyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vacancyId);
    }
}
