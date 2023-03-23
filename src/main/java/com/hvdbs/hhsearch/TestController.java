package com.hvdbs.hhsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
public class TestController {
    @GetMapping("/test")
    public Mono<String> test() {
        log.error("[TEST-CONTROLLER] ERROR LOG");
        log.info("[TEST-CONTROLLER] INFO LOG");
        log.debug("[TEST-CONTROLLER] DEBUG LOG");
        log.trace("[TEST-CONTROLLER] TRACE LOG");

        return Mono.just("HELLO, I am TEST controller");
    }
}
