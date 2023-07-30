package com.hvdbs.savra.hhsearchnonclouddiscoveryclient.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("discoveryserver1")
public interface TestFeignClient {
    @GetMapping(value = "/hello", consumes="application/json")
    String getHello();
}
