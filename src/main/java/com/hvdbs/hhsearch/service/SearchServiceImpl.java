package com.hvdbs.hhsearch.service;

import com.hvdbs.hhsearch.model.dto.VacanciesRs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    private final WebClient webClient;

    @Value("${hhsearch.user-agent}")
    private String userAgent;

    public Mono<VacanciesRs> findVacancies(String keyword, int page, int perPage) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/vacancies")
                        .queryParam("text", keyword)
                        .queryParam("per_page", 100)
                        .build()
                )
                .headers(httpHeaders -> httpHeaders.add("User-Agent", userAgent))
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(VacanciesRs.class));
    }
}
