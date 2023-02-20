package com.hvdbs.hhsearch.service;

import com.hvdbs.hhsearch.model.dto.VacanciesRs;
import reactor.core.publisher.Mono;

public interface SearchService {
    Mono<VacanciesRs> findVacancies(String keyword, int page, int perPage);
}
