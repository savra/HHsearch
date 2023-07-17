package com.hvdbs.savra.hhsearchsearchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hvdbs.savra.hhsearchsearchservice.dataservice.SearchDataService;
import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacanciesRs;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    @Value("${app.base-url}")
    private String baseUrl;
    private static final int PER_PAGE = 100;
    private final RestTemplate restTemplate;
    private final SearchDataService searchDataService;

    @CircuitBreaker(name = "search-service", fallbackMethod = "defaultVacancy")
    public int findVacancies(String keyword) {
        VacanciesRs vacancies = findVacancies(keyword, 0, PER_PAGE);
        int totalPages = vacancies.getPages();

        log.info("Получен список вакансий по ключевому слову \"" + keyword + "\". "
                + "Всего найдено вакансий  - " + vacancies.getFound() + ". "
                + "Всего страниц - " + totalPages);

        int successCount = 0;

        successCount += searchDataService.saveAll(vacancies.getItems());

        //TODO Закомментировал, т.к. после чуть больше 100 запросов включается captcha. Будем позже разбираться, что с ней делать
        /*for (int i = 1; i < totalPages; i++) {
            successCount += searchDataService.saveAll(findVacancies(keyword, i, PER_PAGE).getItems());
        }*/

        return successCount;
    }

    //TODO Удалить метод
    public void kafka(String message) throws JsonProcessingException {
        searchDataService.kafka(message);
    }

    public VacanciesRs findVacancies(String keyword, int page, int perPage) {
        return restTemplate.getForEntity(
                        UriComponentsBuilder.fromHttpUrl(baseUrl + "/vacancies")
                                .queryParam("text", keyword)
                                .queryParam("per_page", perPage)
                                .queryParam("page", page)
                                .toUriString(), VacanciesRs.class)
                .getBody();
    }

    private int defaultVacancy(Exception e) {
        log.error("Search service недоступен. Вызван fallback метод", e);

        return 0;
    }
}
