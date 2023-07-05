package com.hvdbs.savra.hhsearchsearchservice.service;

import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacanciesRs;
import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;

public interface SearchService {
    VacanciesRs findVacancies(String keyword, int page, int perPage);
    VacancyItem findVacancy(String vacancyId);
}

