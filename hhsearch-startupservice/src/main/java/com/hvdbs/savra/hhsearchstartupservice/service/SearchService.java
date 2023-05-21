package com.hvdbs.savra.hhsearchstartupservice.service;

import com.hvdbs.hhsearch.model.dto.VacanciesRs;
import com.hvdbs.hhsearch.model.dto.VacancyItem;
import reactor.core.publisher.Mono;

public interface SearchService {
    Mono<VacanciesRs> findVacancies(String keyword, int page, int perPage);
    Mono<VacancyItem> findVacancy(String vacancyId);
}
