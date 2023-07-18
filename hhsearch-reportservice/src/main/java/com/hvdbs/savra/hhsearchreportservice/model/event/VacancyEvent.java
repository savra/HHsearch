package com.hvdbs.savra.hhsearchreportservice.model.event;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
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
