package com.hvdbs.hhsearch.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VacanciesRs {
    private final List<VacancyItem> vacancyItemList = new ArrayList<>();
    private Pagination pagination;
}
