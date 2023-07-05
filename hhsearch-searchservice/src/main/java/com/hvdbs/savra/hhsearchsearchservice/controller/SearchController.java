package com.hvdbs.savra.hhsearchsearchservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/search")
@RestController
public class SearchController {

    @PostMapping("/vacancies/{keyword}")
    void findVacancies(@PathVariable String keyword) {
        System.out.println("Получено ключевое слово " + keyword);
    }
}
