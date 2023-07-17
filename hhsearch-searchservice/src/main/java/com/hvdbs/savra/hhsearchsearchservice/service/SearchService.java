package com.hvdbs.savra.hhsearchsearchservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SearchService {
    int findVacancies(String keyword);

    void kafka(String message) throws JsonProcessingException;
}

