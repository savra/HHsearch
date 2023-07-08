package com.hvdbs.savra.hhsearchstartupservice.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Configuration
public class WebClientConfiguration {
    private static final int TIMEOUT = 5_0000;
    @Value("${hhsearch.searchservice_host}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, TIMEOUT)
                        .doOnConnected(connection ->
                                connection.addHandlerLast(new ReadTimeoutHandler(TIMEOUT))
                                        .addHandlerLast(new WriteTimeoutHandler(TIMEOUT)))
                       //.wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                        .resolver(DefaultAddressResolverGroup.INSTANCE));

        return WebClient.builder()
                .baseUrl(baseUrl)
                .filter(logRequest())
                .filter(logResponse())
                .clientConnector(httpConnector)
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("""
            Request
                Method: {}
                URL: {}
            """, clientRequest.method(), clientRequest.url());

            log.info("Query params:");
            clientRequest.attributes().forEach((paramName, value) -> log.info("{}: {}", paramName,  value));

            log.info("Request headers:");
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}: {}", name, value)));

            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.info("Response status: {}", clientResponse.statusCode());

            log.info("Response headers:");
            clientResponse.headers()
                    .asHttpHeaders()
                    .forEach((name, values) -> values.forEach(value -> log.info("{}: {}", name, value)));

            return Mono.just(clientResponse);
        });
    }
}
