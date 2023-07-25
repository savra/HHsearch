package com.hvdbs.savra.hhsearchconfigurationserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class HhsearchConfigurationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HhsearchConfigurationServerApplication.class, args);
    }
}