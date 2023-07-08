package com.hvdbs.savra.hhsearchsearchservice.dataservice;

import com.hvdbs.savra.hhsearchsearchservice.mapper.VacancyMapper;
import com.hvdbs.savra.hhsearchsearchservice.model.dto.VacancyItem;
import com.hvdbs.savra.hhsearchsearchservice.model.entity.Vacancy;
import com.hvdbs.savra.hhsearchsearchservice.repository.VacancyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchDataServiceImpl implements SearchDataService {
    private static final String userAgent = "HHsearch/1.0 (savra.sv@yandex.ru)";
    @Value("${app.base-url}")
    private String baseUrl;
    private final VacancyMapper vacancyMapper;
    private final VacancyRepository vacancyRepository;
    private final RestTemplate restTemplate;

    @Override
    public int saveAll(List<VacancyItem> vacanciesItems) {
        int successCount = 0;
        List<Vacancy> vacancyList = new ArrayList<>();

        for (VacancyItem vacancyItem : vacanciesItems) {
            vacancyList.add(vacancyMapper.toEntity(findVacancy(vacancyItem.getVacancyId())));

            successCount++;
        }

        vacancyRepository.saveAll(vacancyList);

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