package com.hvdbs.savra.hhsearchsearchservice.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VacanciesRs {
    private final List<VacancyItem> items = new ArrayList<>();
    private int found;
    private int page;
    private int pages;
    private int perPage;
}
