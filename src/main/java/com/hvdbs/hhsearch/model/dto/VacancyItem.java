package com.hvdbs.hhsearch.model.dto;

import jakarta.validation.constraints.NotNull;

public class VacancyItem {
    @NotNull
    private String id;
    @NotNull
    private String name;
    private boolean premium;
}
