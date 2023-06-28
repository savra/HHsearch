package com.hvdbs.savra.hhsearchstartupservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${hhsearch.searchservice_host}")
    private String searchserviceHost;

    @Override
    public void readKeyword() {
        log.info("Запустилось чтение файла с ключевыми словами");

        try (InputStream keywords = getClass().getClassLoader().getResourceAsStream("search_keywords.txt")) {
            if (keywords != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(keywords, StandardCharsets.UTF_8))) {
                    webClient.post()
                            .uri(searchserviceHost)
                            .bodyValue(objectMapper.writeValueAsString(br.readLine()))
                            .retrieve()
                            .bodyToMono(Void.class)
                            .subscribe();
                }
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла с ключевыми словами", e);
        }
    }
}
