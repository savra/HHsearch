package com.hvdbs.hhsearch;

import com.hvdbs.hhsearch.mapper.VacancyMapper;
import com.hvdbs.hhsearch.model.dto.VacancyItem;
import com.hvdbs.hhsearch.repository.VacancyRepository;
import com.hvdbs.hhsearch.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class HHsearchApplication {
    private final SearchService searchService;
    private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;

    @Value("${hhsearch.search-result.per-page}")
    private int perPage;

    public static void main(String[] args) {
        SpringApplication.run(HHsearchApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws URISyntaxException, IOException {
        AtomicInteger pageCount = new AtomicInteger(1);

        Flux.fromStream(Files.readAllLines(
                                Paths.get(Objects.requireNonNull(
                                                getClass()
                                                        .getClassLoader()
                                                        .getResource("search_keywords.txt"))
                                        .toURI()))
                        .stream())
                .flatMap(keyword ->
                        Flux.fromStream(Stream.iterate(0, page -> page < pageCount.get(), page -> page + 1))
                                .flatMap(page -> searchService.findVacancies(keyword, page, perPage)
                                        .map(vacanciesRs -> {
                                            if (vacanciesRs.getPage() == 0) {
                                                pageCount.set(vacanciesRs.getPages());
                                            }

                                            return vacanciesRs.getItems()
                                                    .stream()
                                                    .map(VacancyItem::getVacancyId);
                                        })
                                        .flatMapMany(Flux::fromStream)
                                        .take(1)
                                        .map(searchService::findVacancy)
                                        .flatMap(vacancyItem -> vacancyItem.map(vacancyMapper::toEntity)))
                )
                .subscribe(vacancyRepository::save);
    }
}
