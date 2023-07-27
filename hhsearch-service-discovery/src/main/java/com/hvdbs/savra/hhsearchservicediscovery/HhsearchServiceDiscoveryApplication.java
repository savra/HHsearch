package com.hvdbs.savra.hhsearchservicediscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class HhsearchServiceDiscoveryApplication {
	public static void main(String[] args) {
		SpringApplication.run(HhsearchServiceDiscoveryApplication.class, args);
	}
}