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

    public static void main(String[] args) {
        SpringApplication.run(HhsearchStartupserviceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        fileService.readKeywords();
    }
}
