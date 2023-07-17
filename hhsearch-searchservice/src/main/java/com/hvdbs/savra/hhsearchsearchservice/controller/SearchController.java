package com.hvdbs.savra.hhsearchsearchservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hvdbs.savra.hhsearchsearchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
@RestController
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/vacancies/{keyword}")
    int findVacancies(@PathVariable String keyword) {
        return searchService.findVacancies(keyword);
    }

    @GetMapping("/vacancies/kafka")
    void kafka(String message) throws JsonProcessingException {
        searchService.kafka(message);
    }
}
