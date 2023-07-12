package com.hvdbs.savra.hhsearchsearchservice.dataservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hvdbs.savra.hhsearchsearchservice.mapper.VacancyMapper;
import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;
import com.hvdbs.savra.hhsearchsearchservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchsearchservice.model.event.VacancyEvent;
import com.hvdbs.savra.hhsearchsearchservice.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchDataServiceImpl implements SearchDataService {
    private static final String userAgent = "HHsearch/1.0 (savra.sv@yandex.ru)";
    @Value("${app.base-url}")
    private String baseUrl;
    private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${app.kafka.topic}")
    private String vacanciesTopic;
    private final ObjectMapper objectMapper;

    @Override
    public int saveAll(List<VacancyItem> vacanciesItems) {
        int successCount = 0;
        List<Vacancy> vacancyList = new ArrayList<>();

        for (VacancyItem vacancyItem : vacanciesItems) {
            Vacancy vacancy = vacancyMapper.toEntity(findVacancy(vacancyItem.getVacancyId()));
            vacancyList.add(vacancy);

            successCount++;
        }

        vacancyRepository.saveAll(vacancyList);

        for (Vacancy vacancy : vacancyList) {
            try {
                VacancyEvent vacancyEvent = new VacancyEvent();
                vacancyEvent.setName(vacancy.getName());
                vacancyEvent.setUrl(vacancy.getAlternateUrl());
                vacancyEvent.setExperience(vacancy.getExperience().getDescription());
                vacancyEvent.setLowerBoundarySalary(vacancy.getSalary().getLowerBoundary());
                vacancyEvent.setUpperBoundarySalary(vacancy.getSalary().getUpperBoundary());
                vacancyEvent.setKeySkills(String.join(",", vacancy.getKeySkills()));

                kafkaTemplate.send(vacanciesTopic, objectMapper.writeValueAsString(vacancyEvent));
            } catch (JsonProcessingException e) {
                log.error("Ошибка сериализации Вакансии с id " + vacancy.getVacancyId());
            }
        }

        return successCount;
    }

    public VacancyItem findVacancy(String vacancyId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", userAgent);

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return restTemplate.exchange(baseUrl + "/vacancies/" + vacancyId,
                        HttpMethod.GET, entity, VacancyItem.class)
                .getBody();
    }
}