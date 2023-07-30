package com.hvdbs.savra.hhsearchnonclouddiscoveryserver1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        log.info("DISCOVERY-SERVER-1: " + System.nanoTime() / 1e6 + " ms");

        return "Hello from DISCOVERY-SERVER-1";
    }
}
