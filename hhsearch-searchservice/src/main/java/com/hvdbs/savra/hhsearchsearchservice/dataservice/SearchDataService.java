package com.hvdbs.savra.hhsearchsearchservice.dataservice;

import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;

import java.util.List;

public interface SearchDataService {
    int saveAll(List<VacancyItem> vacanciesItems);
}