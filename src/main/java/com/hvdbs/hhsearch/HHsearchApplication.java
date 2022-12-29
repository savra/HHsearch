package com.hvdbs.hhsearch;

import com.hvdbs.hhsearch.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class HHsearchApplication {
    private final SearchService service;

    @Value("${search-result.per-page}")
    private int perPage;

    public static void main(String[] args) {
        SpringApplication.run(HHsearchApplication.class, args);
    }

    //@Scheduled
    @EventListener(ApplicationReadyEvent.class)
    public void run() throws URISyntaxException, IOException {


        List<String> allLines = Files.readAllLines(
                Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                                .getResource("search_keywords.txt"))
                        .toURI()));

        service.findVacancies();
    }
}
