package com.hvdbs.savra.hhsearchreportservice.controller;

import com.hvdbs.savra.hhsearchreportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@RestController
public class ReportController {
    private final ReportService reportService;

    @CrossOrigin
    @GetMapping
    public byte[] report() throws IOException {
        return reportService.getReport();
    }
}
