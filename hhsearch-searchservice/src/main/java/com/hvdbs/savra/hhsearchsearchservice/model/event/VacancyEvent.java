package com.hvdbs.savra.hhsearchsearchservice.model.event;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VacancyEvent {
    @NotBlank
    private String name;
    private String experience;
    @NotBlank
    private String url;
    private BigDecimal lowerBoundarySalary;
    private BigDecimal upperBoundarySalary;
    private String keySkills;
    private String currency;
    private Boolean gross;
}
