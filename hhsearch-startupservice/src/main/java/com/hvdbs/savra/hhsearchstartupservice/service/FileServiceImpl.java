package com.hvdbs.savra.hhsearchstartupservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private static final String SEARCH_SERVICE_HOST = "/search/vacancies/{keyword}";
    private final WebClient webClient;

    @Override
    public void readKeywords() {
        log.info("Запустилось чтение файла с ключевыми словами");

        try (InputStream keywordsFile = getClass().getClassLoader().getResourceAsStream("search_keywords.txt")) {
            if (keywordsFile != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(keywordsFile, StandardCharsets.UTF_8))) {
                    Set<String> keywords = br.lines().collect(Collectors.toSet());

                    Flux.fromIterable(keywords)
                            .log()
                            .limitRate(10)
                            .flatMap(keyword ->
                                    webClient.get()
                                            .uri(SEARCH_SERVICE_HOST, keyword)
                                            .retrieve()
                                            .bodyToMono(Integer.class)
                                            .map(successCount -> new AbstractMap.SimpleEntry<>(keyword, successCount))
                                            .doOnError(throwable ->
                                                    log.error(String.format("Во время отправки ключевого слова \"%s\" в search service произошла ошибка: %s",
                                                            keyword,
                                                            throwable.getMessage())))
                            )
                            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)).jitter(0.75))
                            .doOnNext(response -> log.info("Ключевое слово \"" + response.getKey()
                                    + "\" обработано. Сохранено " + response.getValue() + " вакансий"))
                            .doFinally(signalType -> log.info("Все ключевые слова обработаны"))
                            .subscribe();
                }
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла с ключевыми словами", e);
        }
    }
}
