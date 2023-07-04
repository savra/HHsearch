package com.hvdbs.savra.hhsearchstartupservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private static final String SEARCH_SERVICE_HOST = "/search/vacancies/{keyword}";
    private final WebClient webClient;

    @Override
    public void readKeywords() {
        log.info("Запустилось чтение файла с ключевыми словами %s");

        try (InputStream keywords = getClass().getClassLoader().getResourceAsStream("search_keywords.txt")) {
            if (keywords != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(keywords, StandardCharsets.UTF_8))) {
                    Flux.fromStream(br.lines())
                            .log()
                            .limitRate(8)
                            .flatMap(keyword ->
                                    webClient.post()
                                            .uri(SEARCH_SERVICE_HOST, keyword)
                                            .retrieve()
                                            .bodyToMono(Void.class)
                                            .doOnError(throwable ->
                                                    log.error(String.format("Во время отправки ключевого слова \"%s\" в search service произошла ошибка: %s",
                                                            keyword,
                                                            throwable.getMessage()))))
                            .doFinally(signalType -> log.info("Файл прочитан, данные отправлены в searchservice"))
                            .subscribe();
                }
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла с ключевыми словами", e);
        }
    }
}
