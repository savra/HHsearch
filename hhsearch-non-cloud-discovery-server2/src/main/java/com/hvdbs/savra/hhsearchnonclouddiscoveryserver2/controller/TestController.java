package com.hvdbs.savra.hhsearchnonclouddiscoveryserver2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @GetMapping("/hello")
    public void hello() {
        log.info("DISCOVERY-SERVER-2: " + System.nanoTime() / 1e6 + " ms");
    }
}
