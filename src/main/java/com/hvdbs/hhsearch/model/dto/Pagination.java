package com.hvdbs.hhsearch.model.dto;

import lombok.Data;

@Data
public class Pagination {
    private int found;
    private int page;
    private int pages;
    private int perPage;
}
