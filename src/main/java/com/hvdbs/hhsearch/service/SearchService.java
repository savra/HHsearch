package com.hvdbs.hhsearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class SearchService {
    private final WebClient webClient;

    public Mono<String> findVacancies(String keyword) {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/vacancies")
                        .queryParam("text", keyword)
                        .build()
                )
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class));
    }
}
