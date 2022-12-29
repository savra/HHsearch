package com.hvdbs.hhsearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;


@RequiredArgsConstructor
@Service
public class SearchService {
    private final WebClient webClient;

    public void findVacancies() {
        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/vacancies")
                        .queryParam("text", "java разработчик")
                        .build()
                )
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(String.class))
                .subscribe(
                        data -> System.out.println(data),
                        err -> System.out.println(err)
                );
    }
}
