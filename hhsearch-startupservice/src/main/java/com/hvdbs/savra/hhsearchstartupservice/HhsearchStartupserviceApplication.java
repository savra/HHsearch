package com.hvdbs.savra.hhsearchstartupservice;

import com.hvdbs.savra.hhsearchstartupservice.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@SpringBootApplication
public class HhsearchStartupserviceApplication {
    private final FileService fileService;
   /* private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;

    private static final int perPage = 100;*/

    public static void main(String[] args) {
        SpringApplication.run(HhsearchStartupserviceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        fileService.readKeywords();
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
}
