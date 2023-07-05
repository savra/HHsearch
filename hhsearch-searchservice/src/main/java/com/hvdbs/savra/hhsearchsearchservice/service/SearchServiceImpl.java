package com.hvdbs.savra.hhsearchsearchservice.service;

import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacanciesRs;
import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    private static final String userAgent = "HHsearch/1.0 (savra.sv@yandex.ru)";

    public VacanciesRs findVacancies(String keyword, int page, int perPage) {
        return null; /*webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/vacancies")
                        .queryParam("text", keyword)
                        .queryParam("per_page", perPage)
                        .build()
                )
                .headers(httpHeaders -> httpHeaders.add("User-Agent", userAgent))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(VacanciesRs.class));*/
    }

    @Override
    public VacancyItem findVacancy(String vacancyId) {
        return null; /*webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/vacancies/" + vacancyId).build())
                .headers(httpHeaders -> httpHeaders.add("User-Agent", userAgent))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(VacancyItem.class));*/
    }
}
