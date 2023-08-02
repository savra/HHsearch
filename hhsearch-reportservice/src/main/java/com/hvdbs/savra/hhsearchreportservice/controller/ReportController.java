package com.hvdbs.savra.hhsearchreportservice.controller;

import com.hvdbs.savra.hhsearchreportservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
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

    @CrossOrigin
    @GetMapping("/test")
    public String report3() {
        log.info("I here!");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "HELLO FROM REPORT SERVICE";
    }
}
