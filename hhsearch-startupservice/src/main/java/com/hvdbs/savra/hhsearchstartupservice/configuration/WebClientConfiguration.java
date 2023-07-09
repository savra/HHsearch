package com.hvdbs.savra.hhsearchstartupservice.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Slf4j
@Configuration
public class WebClientConfiguration {
    private static final int TIMEOUT = 5_0000;
    @Value("${app.search-service.host}")
    private String baseUrl;
    @Value("${app.ssl.key-store-type}")
    private String keyStoreType;
    @Value("${app.ssl.key-store}")
    private String keyStorePath;
    @Value("${app.ssl.key-store-password}")
    private String keyStorePassword;
    @Value("${app.ssl.trust-store}")
    private String trustStorePath;
    @Value("${app.ssl.trust-store-password}")
    private String trustStorePassword;
    @Value("${app.ssl.trust-store-type}")
    private String trustStoreType;

    @Bean
    public WebClient webClient() throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(new FileInputStream((ResourceUtils.getFile(keyStorePath))), keyStorePassword.toCharArray());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

        KeyStore trustStore = KeyStore.getInstance(trustStoreType);
        trustStore.load(new FileInputStream((ResourceUtils.getFile(trustStorePath))), trustStorePassword.toCharArray());
        trustManagerFactory.init(trustStore);

        SslContext sslContext = SslContextBuilder
                .forClient()
                .keyManager(keyManagerFactory)
                .trustManager(trustManagerFactory)
                .build();

        ReactorClientHttpConnector httpConnector = new ReactorClientHttpConnector(
                HttpClient.create()
                        .secure(sslSpec -> sslSpec.sslContext(sslContext))
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
