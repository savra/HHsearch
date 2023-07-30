package com.hvdbs.savra.hhsearchnonclouddiscoveryclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TestController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestFeignClient testFeignClient;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/discovery-client")
    public void testDiscoveryClient() {
        RestTemplate restTemplate = new RestTemplate();
        List<String> services = discoveryClient.getServices();
        List<ServiceInstance> instances = discoveryClient.getInstances("discoveryserver1");
    }

    @GetMapping("/loadbalancer")
    public void testLoadBalancer() {
        String results = restTemplate.getForObject("http://discoveryserver1/hello", String.class);

        int f = 6;
    }

    @GetMapping("/feignclient")
    public void testFeignClient() {
        testFeignClient.getHello();
    }
}
