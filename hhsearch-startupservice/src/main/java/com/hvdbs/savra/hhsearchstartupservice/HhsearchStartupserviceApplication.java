package com.hvdbs.savra.hhsearchstartupservice;

import com.hvdbs.savra.hhsearchstartupservice.mapper.VacancyMapper;
import com.hvdbs.savra.hhsearchstartupservice.repository.VacancyRepository;
import com.hvdbs.savra.hhsearchstartupservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class HhsearchStartupserviceApplication {
    private final SearchService searchService;
    private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;

    private static final int perPage = 100;

    public static void main(String[] args) {
        SpringApplication.run(HhsearchStartupserviceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        refreshVacancies();
//        AtomicInteger pageCount = new AtomicInteger(1);
//        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("search_keywords.txt")) {
//            if (resourceAsStream != null) {
//                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
//                    Flux.fromStream(bufferedReader.lines())
//                            .flatMap(keyword ->
//                                    Flux.fromStream(Stream.iterate(0, page -> page < pageCount.get(), page -> page + 1))
//                                            .flatMap(page -> searchService.findVacancies(keyword, page, perPage)
//                                                    .map(vacanciesRs -> {
//                                                        if (vacanciesRs.getPage() == 0) {
//                                                            pageCount.set(vacanciesRs.getPages());
//                                                        }
//
//                                                        return vacanciesRs.getItems()
//                                                                .stream()
//                                                                .map(VacancyItem::getVacancyId);
//                                                    })
//                                                    .flatMapMany(Flux::fromStream)
//                                                    .take(1)
//                                                    .map(searchService::findVacancy)
//                                                    .flatMap(vacancyItem -> vacancyItem.map(vacancyMapper::toEntity)))
//                            )
//                            .subscribe(vacancyRepository::save);
//                }
//            }
//        }
    }

    //TODO  @Scheduled() Запуск чтения ключевых
    // слов из файла и отправка в сервис поиска
    // поиска вакансий по ключевым словам каждый день в 22:00 мск - получить вакансии и сохранить в базу
    //
    private void refreshVacancies() {
        try (InputStream keywords = getClass().getClassLoader().getResourceAsStream("search_keywords.txt")) {
            if (keywords != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(keywords, StandardCharsets.UTF_8))) {
                    br.lines().forEach(log::info);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка чтения файла с ключевыми словами", e);
        }
        /*
        1. Текущий сервис CommonService в методе @Scheduled() инициализирует запуск чтения ключевых
        слов из файла и отправляет эти ключевые слова в searchService post-запросом (Тут как раз область для проверки rateLimit - ограничение скорости отправки, чтобы не
        задудосить serarchService
        2. searchService запрашивает вакансии из HH по ключевым словам и сохраняет их в свою схему БД и отправляет вакансии в топик vacancies в кафку
        *
        3. reportService читает необработанные записи из кафки и сохраняет в свою схему БД. Далее читает из БД парсит данные, формирует отчет и
        // сохраняет этот отчет в БД и отправляет в кафку сообщение о том, что был сформирован новый отчет с именем отчета
        // mailService по событию из кафки читает из бады отчет и с прочитанным из кафки именем и отправляет отчет на email
        */
    }
}
