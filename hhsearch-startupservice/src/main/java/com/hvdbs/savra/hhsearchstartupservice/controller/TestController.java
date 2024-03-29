package com.hvdbs.savra.hhsearchstartupservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@RestController
public class TestController {
    private final WebClient webClient;

    @GetMapping("/test")
    public Mono<String> test() {
        log.trace("""
                [TEST-CONTROLLER]!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                """);

        log.error("[TEST-CONTROLLER] ERROR LOG");
        log.info("[TEST-CONTROLLER] INFO LOG");
        log.debug("[TEST-CONTROLLER] DEBUG LOG");
        log.trace("[TEST-CONTROLLER] TRACE LOG");

        return Mono.just("HELLO, I am TEST controller");
    }

    @GetMapping("/kafka")
    public void kafka() {
        webClient.get()
                .uri("/search/vacancies/kafka")
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
